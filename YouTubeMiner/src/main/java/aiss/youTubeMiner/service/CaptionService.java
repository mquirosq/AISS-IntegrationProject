package aiss.youTubeMiner.service;

import aiss.youTubeMiner.model.caption.Caption;
import aiss.youTubeMiner.model.caption.CaptionSearch;
import aiss.youTubeMiner.model.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.model.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaptionService {
    @Autowired
    RestTemplate restTemplate;

    final String key = "AIzaSyCgo33WDq8_uoH6tWH6COhTmemxQbimDHY";

    public List<Caption> getCaptions(String videoId) {
        String uri = "https://www.googleapis.com/youtube/v3/captions";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + key);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CaptionSearch> request = new HttpEntity<>(headers);

        ResponseEntity<CaptionSearch> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                CaptionSearch.class
        );
        return response.getBody().getItems();
    }
}
