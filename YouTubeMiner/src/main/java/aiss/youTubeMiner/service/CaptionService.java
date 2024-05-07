package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CaptionNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.helper.Constants;
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

    public List<Caption> getCaptions(String videoId) throws CaptionNotFoundException {
        String uri = Constants.ytBase + "/captions";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + Constants.apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CaptionSearch> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<CaptionSearch> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    CaptionSearch.class
            );
            return response.getBody().getItems();
        } catch (NullPointerException|RestClientResponseException e) {
            throw new CaptionNotFoundException();
        }
    }

    public VCaption createCaption(String videoId, Caption caption) throws VideoMinerConnectionRefusedException {
        try {
            String uri = Constants.vmBase + "/videos/" + videoId + "/captions";
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
