package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CaptionNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.oauth2.Authenticator;
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

    @Autowired
    Authenticator authenticator;

    public List<Caption> getCaptions(String videoId, Boolean test) throws CaptionNotFoundException, OAuthException {
        String uri = ConstantsHelper.ytBaseUri + "/captions";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");

        HttpHeaders headers = null;

        if (test) {
            uri += ("&key=" + ConstantsHelper.apiKey);
        } else {
            headers = authenticator.getAuthHeader();
        }

        try {
            ResponseEntity<CaptionSearch> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    CaptionSearch.class
            );
            return response.getBody().getItems();
        } catch (NullPointerException|HttpClientErrorException.NotFound e) {
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
