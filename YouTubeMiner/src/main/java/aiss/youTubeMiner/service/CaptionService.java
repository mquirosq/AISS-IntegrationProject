package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CaptionNotFoundException;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.youTubeModel.caption.CaptionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CaptionService {
    @Autowired
    RestTemplate restTemplate;

    public List<Caption> getCaptions(String videoId) throws CaptionNotFoundException {
        String uri = ConstantsHelper.ytBaseUri + "/captions";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + ConstantsHelper.apiKey);

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
        } catch (HttpClientErrorException.NotFound e) {
            throw new CaptionNotFoundException();
        }
    }

    public VCaption transformCaption(Caption caption) {
        VCaption out = new VCaption();
        out.setId(caption.getId());
        out.setName(caption.getSnippet().getName());
        out.setLanguage(caption.getSnippet().getLanguage());
        return out;
    }
}
