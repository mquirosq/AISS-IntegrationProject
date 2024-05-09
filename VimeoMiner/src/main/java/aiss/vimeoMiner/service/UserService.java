package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.CommentNotFoundException;
import aiss.vimeoMiner.exception.UserNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.videoModel.VUser;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelUser.ModelUser;
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

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public ModelUser getUser(String userUri) throws UserNotFoundException {
        String uri = "https://api.vimeo.com" + userUri;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ee507ffdb4da956d56252e8eb067fb58");

        try {
            ResponseEntity<ModelUser> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ModelUser.class);
            return response.getBody();
        } catch (RestClientResponseException ex) {
            throw new UserNotFoundException();
        }
    }
    public VUser createUser(ModelUser modelUser) throws VideoMinerConnectionRefusedException, CommentNotFoundException {
        String uri = "http://localhost:8080/videoMiner/v1/users";
        try {
            // Convert properties:
            VUser vUser = transformUser(modelUser);
            // Http request
            HttpEntity<VUser> request = new HttpEntity<>(vUser);
            ResponseEntity<VUser> response = restTemplate.exchange(uri, HttpMethod.POST, request, VUser.class);
            VUser createdUser = response.getBody();
            return createdUser;
        }
        catch(HttpClientErrorException.NotFound e) {
            throw new CommentNotFoundException();
        }
        // Catch connection exceptions
        catch(ResourceAccessException err){

            throw new VideoMinerConnectionRefusedException();
        }
    }
    public VUser transformUser(ModelUser modelUser) {
        VUser vUser = new VUser();
        vUser.setUser_link(modelUser.getLink());
        vUser.setName(modelUser.getName());
        vUser.setPicture_link(modelUser.getPictures().getBaseLink());

        return vUser;
    }
}


