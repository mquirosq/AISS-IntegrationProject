package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import aiss.vimeoMiner.vimeoModel.modelCaption.Captions;
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

    public List<Caption> getCaptions(String captionUri){
        // URI
        String uri = "https://api.vimeo.com" + captionUri;

        // Header for authentication
        HttpHeaders header = new HttpHeaders(){
            {
                String auth = "Bearer ee507ffdb4da956d56252e8eb067fb58";
                set("Authorization", auth);
            }
        };

        // Send message
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
    }

    // Get next page URL
    public static String getNextPageUrl(HttpHeaders headers) {
        String result = null;

        // If there is no link header, return null
        List<String> linkHeader = headers.get("Link");
        if (linkHeader == null)
            return null;

        // If the header contains no links, return null
        String links = linkHeader.get(0);
        if (links == null || links.isEmpty())
            return null;

        // Return the next page URL or null if none.
        for (String token : links.split(", ")) {
            if (token.endsWith("rel=\"next\"")) {
                // Found the next page. This should look something like
                // <https://api.github.com/repos?page=3&per_page=100>; rel="next"
                int idx = token.indexOf('>');
                result = token.substring(1, idx);
                break;
            }
        }

        return result;
    }

}
