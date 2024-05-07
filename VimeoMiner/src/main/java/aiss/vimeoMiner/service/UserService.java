package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.vimeoModel.modelUser.ModelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public ModelUser getUser(String userId) throws ChannelNotFoundException {
        String uri = "https://api.vimeo.com/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ee507ffdb4da956d56252e8eb067fb58");

        try {
            System.out.println(uri+"this is an uri");
            ResponseEntity<ModelUser> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ModelUser.class);
            return response.getBody();
        } catch (RestClientResponseException ex) {
            throw new ChannelNotFoundException();
        }
    }

    public ModelUser postUser(ModelUser user) {
        String uri = "https://api.vimeo.com/users/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ee507ffdb4da956d56252e8eb067fb58");

        try {
            HttpEntity<ModelUser> request = new HttpEntity<>(user, headers);
            ResponseEntity<ModelUser> response = restTemplate.exchange(uri, HttpMethod.POST, request, ModelUser.class);
            return response.getBody();
        } catch (RestClientResponseException ex) {
            // Handle exception appropriately
            ex.printStackTrace();
            return null;
        }
    }
}


