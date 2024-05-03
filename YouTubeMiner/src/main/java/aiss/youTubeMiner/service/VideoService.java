package aiss.youTubeMiner.service;

import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    RestTemplate restTemplate;

    final String key = "AIzaSyCgo33WDq8_uoH6tWH6COhTmemxQbimDHY";

    public List<VideoSnippet> getVideos(String channelId) {
        List<VideoSnippet> out = new ArrayList<>();
        String uri = "https://www.googleapis.com/youtube/v3/search";
        uri += ("?channelId=" + channelId);
        uri += ("&type=" + "video");
        uri += ("&part=" + "snippet");
        uri += ("&key=" + key);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VideoSnippetSearch> request = new HttpEntity<>(headers);

        ResponseEntity<VideoSnippetSearch> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                VideoSnippetSearch.class
        );

        if (response.getBody() != null) {
            out.addAll(response.getBody().getItems());
        }

        String next = getNextPage(uri, response);

        while (next != null) {
            response = restTemplate.exchange(next, HttpMethod.GET, null, VideoSnippetSearch.class);

            if (response.getBody() != null) {
                out.addAll(response.getBody().getItems());
            }
            next = getNextPage(uri, response);
        }
        return out;
    }

    public String getNextPage(String uri, ResponseEntity<VideoSnippetSearch> response) {
        String next = response.getBody().getNextPageToken();

        if (next == null) {
            return null;
        }
        return uri + ("&pageToken=" + next);
    }
}
