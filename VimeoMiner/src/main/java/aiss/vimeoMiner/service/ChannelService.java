package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


@Service
public class ChannelService {
    @Autowired
    RestTemplate restTemplate;

    // Get from Vimeo API
    public Channel getChannel(String channelId){
        // URI
        String uri = "https://api.vimeo.com/channels/" + channelId;

        // Header for authentication
        HttpHeaders header = new HttpHeaders(){
            {
                String auth = "Bearer ee507ffdb4da956d56252e8eb067fb58";
                set("Authorization", auth);
            }
        };

        // Send message
        ResponseEntity<Channel> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Channel>(header),Channel.class);
        return response.getBody();
    }

    // Post to VideoMiner:
    public Channel createChannel(Channel channel){
        String uri = "http://localhost:8080/videoMiner/v1/channels";
        try {
            HttpEntity<Channel> request = new HttpEntity<>(channel);
            ResponseEntity<Channel> response = restTemplate.exchange(uri, HttpMethod.POST, request, Channel.class);
            Channel createdChannel = response.getBody();
            return createdChannel;
        }
        catch(RestClientResponseException err) {
            System.out.println("Error when creating the channel " + channel + ":"+ err.getLocalizedMessage());
            return null;
        }
    }

}
