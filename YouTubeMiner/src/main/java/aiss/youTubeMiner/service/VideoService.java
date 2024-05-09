package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.helper.Constants;
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

    public List<VideoSnippet> getVideos(String channelId) throws VideoNotFoundException {
        String uri = Constants.ytBase + "/search";
        uri += ("?channelId=" + channelId);
        uri += ("&type=" + "video");
        uri += ("&part=" + "snippet");
        uri += ("&key=" + Constants.apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VideoSnippetSearch> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<VideoSnippetSearch> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    VideoSnippetSearch.class
            );
            List<VideoSnippet> out = new ArrayList<>();
            String next = null;

            if (response.getBody() != null) {
                out.addAll(response.getBody().getItems());
                next = getNextPage(uri, response);
            }

            while (next != null) {
                response = restTemplate.exchange(next, HttpMethod.GET, null, VideoSnippetSearch.class);

                if (response.getBody() != null) {
                    out.addAll(response.getBody().getItems());
                }
                next = getNextPage(uri, response);
            }
            return out;
        } catch (RestClientResponseException e) {
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

    public VVideo createVideo(String channelId, VideoSnippet video) throws ChannelNotFoundException, VideoMinerConnectionRefusedException {
        try {
            String uri = Constants.vmBase + "/channels/" + channelId + "/videos";
            VVideo vVideo = mapVideo(video);
            HttpEntity<VVideo> request = new HttpEntity<>(vVideo);
            ResponseEntity<VVideo> response = restTemplate.exchange(uri, HttpMethod.POST, request, VVideo.class);
            return response.getBody();
        } catch(HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException();
        } catch (ResourceAccessException e) {
            throw new VideoMinerConnectionRefusedException();
        }
    }

    private VVideo mapVideo(VideoSnippet video) {
        VVideo out = new VVideo();
        out.setId(video.getId().toString());
        out.setName(video.getSnippet().getTitle());
        out.setDescription(video.getSnippet().getDescription());
        out.setReleaseTime(video.getSnippet().getPublishedAt());
        out.setCaptions(new ArrayList<>());
        out.setComments(new ArrayList<>());
        return out;
    }
}
