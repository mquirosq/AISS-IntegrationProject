package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.GlobalExceptionHandler;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


@Service
public class ChannelService {
    @Autowired
    RestTemplate restTemplate;

    // Get from Vimeo API
    public Channel getChannel(String channelId) throws ChannelNotFoundException {
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
        try {
            ResponseEntity<Channel> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Channel>(header),Channel.class);
            return response.getBody();
        }
        catch (Exception err) {
            throw new ChannelNotFoundException();
        }
    }

    // Post to VideoMiner:
    public Channel createChannel(Channel channel){
        String uri = "http://localhost:8080/videoMiner/v1/channels";
        try {
            // Convert properties:
            channel.setAdditionalProperty("createdTime", channel.getCreatedTime());
            // Http request
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
