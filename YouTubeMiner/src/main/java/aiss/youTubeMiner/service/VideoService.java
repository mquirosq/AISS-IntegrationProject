package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.oauth2.Authenticator;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Authenticator authenticator;

    private String genURI(String channelId, Integer maxVideos, Boolean test) {
        String uri = Constants.ytBase + "/search";
        uri += ("?channelId=" + channelId);
        uri += ("&type=" + "video");
        uri += ("&part=" + "snippet");
        uri += ("&maxResults=" + ((maxVideos > 50) ? 50 : maxVideos));

        if (test) {
            uri += ("&key=" + Constants.apiKey);
        }
        return uri;
    }

    public List<VideoSnippet> getVideos(String channelId, Integer maxVideos, Boolean test) throws VideoNotFoundException, OAuthException {
        HttpHeaders headers = null;
        if (!test) {
            headers = authenticator.getAuthHeader();
        }
        HttpEntity<VideoSnippetSearch> request = new HttpEntity<>(headers);

        try {
            List<VideoSnippet> out = null;
            String next = null;

            ResponseEntity<VideoSnippetSearch> response = restTemplate.exchange(
                    genURI(channelId, maxVideos, test),
                    HttpMethod.GET,
                    request,
                    VideoSnippetSearch.class
            );

            if (response.getBody() != null) {
                out = new ArrayList<>(response.getBody().getItems());
            } else {
                return null;
            }
            next = getNextPage(genURI(channelId, maxVideos - out.size(), test), response);

            while (out.size() < maxVideos && next != null) {
                response = restTemplate.exchange(next, HttpMethod.GET, request, VideoSnippetSearch.class);

                if (response.getBody() != null) {
                    out.addAll(response.getBody().getItems());
                }
                next = getNextPage(genURI(channelId, maxVideos - out.size(), test), response);
            }
            return out;
        } catch (HttpClientErrorException e) {
            throw new VideoNotFoundException();
        }
    }

    public String getNextPage(String uri, ResponseEntity<VideoSnippetSearch> response) throws VideoNotFoundException {
        String next = null;

        try {
            next = response.getBody().getNextPageToken();
        } catch (NullPointerException e) {
            throw new VideoNotFoundException();
        }

        if (next == null) {
            return null;
        }
        return uri + ("&pageToken=" + next);
    }

    public VVideo transformVideo(VideoSnippet video) {
        VVideo out = new VVideo();
        out.setId(video.getId().getVideoId());
        out.setName(video.getSnippet().getTitle());
        out.setDescription(video.getSnippet().getDescription());
        out.setReleaseTime(video.getSnippet().getPublishedAt());
        out.setCaptions(new ArrayList<>());
        out.setComments(new ArrayList<>());
        return out;
    }
}
