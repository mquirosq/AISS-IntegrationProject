package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.youTubeModel.caption.CaptionSearch;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

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

    public VCaption createCaption(String videoId, Caption caption) throws VideoMinerConnectionRefusedException {
        try {
            String uri = "http://localhost:8080/videoMiner/v1/videos/" + videoId + "/captions";
            VCaption vCaption = mapCaption(caption);
            HttpEntity<VCaption> request = new HttpEntity<>(vCaption);
            ResponseEntity<VCaption> response = restTemplate.exchange(uri, HttpMethod.POST, request, VCaption.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error creating caption: " + e.getLocalizedMessage());
            return null;
        } catch (ResourceAccessException e) {
            throw new VideoMinerConnectionRefusedException();
        }
    }

    private VCaption mapCaption(Caption caption) {
        VCaption out = new VCaption();
        out.setId(caption.getId());
        out.setName(caption.getSnippet().getName());
        out.setLanguage(caption.getSnippet().getLanguage());
        return out;
    }
}
