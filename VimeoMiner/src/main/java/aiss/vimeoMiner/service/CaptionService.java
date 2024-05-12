package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.CaptionNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VCaption;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VUser;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import aiss.vimeoMiner.vimeoModel.modelCaption.Captions;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static aiss.vimeoMiner.helper.AuthenticationHelper.createHttpHeaderAuthentication;
import static aiss.vimeoMiner.helper.ConstantsHelper.videoMinerBaseUri;
import static aiss.vimeoMiner.helper.ConstantsHelper.vimeoBaseUri;
import static aiss.vimeoMiner.helper.PaginationHelper.getNextPageUrl;

@Service
public class CaptionService {

    @Autowired
    RestTemplate restTemplate;

    public List<Caption> getCaptions(String captionUri) throws CaptionNotFoundException {
        // URI
        String uri = vimeoBaseUri + captionUri;

        // Header for authentication
        HttpHeaders header = createHttpHeaderAuthentication();

        // Send message
        try {
            ResponseEntity<Captions> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Captions>(header), Captions.class);
            Captions captions = response.getBody();
            List<Caption> captionsArray = new ArrayList<>();
            if (captions != null){
                captionsArray = captions.getData();
            }

            String nextUrl = getNextPageUrl(response.getHeaders());
            while (nextUrl != null) {
                response = restTemplate.exchange(nextUrl, HttpMethod.GET, new HttpEntity<Captions>(header), Captions.class);
                captions = response.getBody();
                if (captions.getData() != null){
                    captionsArray.addAll(captions.getData());
                }
                nextUrl = getNextPageUrl(response.getHeaders());
            }

            return captionsArray;

        } catch (HttpClientErrorException.NotFound err){
            throw new CaptionNotFoundException();
        }

    }

    public VCaption transformCaption(Caption caption){
        VCaption vCaption = new VCaption();
        vCaption.setId(caption.getId().toString());
        vCaption.setName(caption.getName());
        vCaption.setLanguage(caption.getLanguage());
        return vCaption;
    }

}
