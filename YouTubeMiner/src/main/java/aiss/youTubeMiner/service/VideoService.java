package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VComment;
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
    CaptionService captionService;

    @Autowired
    CommentService commentService;

    private String genURI(String channelId, Integer maxVideos) {
        String uri = Constants.ytBaseUri + "/search";
        uri += ("?channelId=" + channelId);
        uri += ("&type=" + "video");
        uri += ("&part=" + "snippet");
        uri += ("&maxResults=" + ((maxVideos > 50) ? 50 : maxVideos));
        uri += ("&key=" + Constants.apiKey);
        return uri;
    }

    public List<VideoSnippet> getVideos(String channelId, Integer maxVideos) throws VideoNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VideoSnippetSearch> request = new HttpEntity<>(headers);

        try {
            List<VideoSnippet> out = null;
            String next = null;
            ResponseEntity<VideoSnippetSearch> response = restTemplate.exchange(
                    genURI(channelId, maxVideos),
                    HttpMethod.GET,
                    request,
                    VideoSnippetSearch.class
            );

            if (response.getBody() != null) {
                out = new ArrayList<>(response.getBody().getItems());
            } else {
                return null;
            }
            next = getNextPage(genURI(channelId, maxVideos - out.size()), response);

            while (out.size() < maxVideos && next != null) {
                response = restTemplate.exchange(next, HttpMethod.GET, request, VideoSnippetSearch.class);

                if (response.getBody() != null) {
                    out.addAll(response.getBody().getItems());
                }
                next = getNextPage(genURI(channelId, maxVideos - out.size()), response);
            }
            return out;
        } catch (HttpClientErrorException.NotFound e) {
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

    public VVideo transformVideo(VideoSnippet video) throws CaptionNotFoundException, VideoCommentsNotFoundException, CommentNotFoundException {
        VVideo out = new VVideo();
        List<VCaption> vCaptions = new ArrayList<>();
        List<VComment> vComments = new ArrayList<>();
        captionService.getCaptions(video.getId().getVideoId()).forEach(ca -> vCaptions.add(captionService.transformCaption(ca)));
        commentService.getCommentsFromVideo(video.getId().getVideoId(),10).forEach(co -> vComments.add(commentService.transformComment(co)));
        out.setId(video.getId().getVideoId());
        out.setName(video.getSnippet().getTitle());
        out.setDescription(video.getSnippet().getDescription());
        out.setReleaseTime(video.getSnippet().getPublishedAt());
        out.setCaptions(vCaptions);
        out.setComments(vComments);
        return out;
    }
}
