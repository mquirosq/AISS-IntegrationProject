package aiss.youTubeMiner.service;

import aiss.youTubeMiner.model.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.model.videoSnippet.VideoSnippetSearch;
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

    public List<VideoSnippet> getVideos(String videosId) {
        List<VideoSnippet> out = new ArrayList<>();

        String uri = "https://www.googleapis.com/youtube/v3/videos";
        uri += ("?id=" + videosId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + key);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<VideoSnippetSearch> request = new HttpEntity<VideoSnippetSearch>(headers);

        ResponseEntity<VideoSnippetSearch> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                VideoSnippetSearch.class
        );

        if (response.getBody() != null) {
            out.addAll(response.getBody().getItems());
        }

        String next = getNextPage(response);

        while (next != null) {
            response = restTemplate.exchange(next, HttpMethod.GET, null, VideoSnippetSearch.class);

            if (response.getBody() != null) {
                out.addAll(response.getBody().getItems());
            }
            next = getNextPage(response);
        }
        return out;
    }

    public String getNextPage(ResponseEntity<VideoSnippetSearch> response) {
        String out = "";
        List<String> linkHeader = response.getHeaders().get("Link");

        if (linkHeader == null)
            return null;

        // If the header contains no links, return null
        String links = linkHeader.get(0);
        if (links == null || links.isEmpty())
            return null;

        // Return the next page URL or null if none.
        for (String s : links.split(", ")) {
            if (s.endsWith("rel=\"next\"")) {
                int idx = s.indexOf('>');
                out = s.substring(1, idx);
                break;
            }
        }
        return out;
    }
}
