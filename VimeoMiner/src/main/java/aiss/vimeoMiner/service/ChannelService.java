package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.GlobalExceptionHandler;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.*;

import java.net.ConnectException;
import java.util.ArrayList;


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
            Channel channel = response.getBody();
            channel.setId(channelId);
            return channel;
        }
        catch (RestClientResponseException err) {
            throw new ChannelNotFoundException();
        }
    }

    // Post to VideoMiner:
    public VChannel createChannel(Channel channel) throws VideoMinerConnectionRefusedException, ChannelNotFoundException {
        String uri = "http://localhost:8080/videoMiner/v1/channels";
        try {
            // Convert properties:
            VChannel vChannel = transformChannel(channel);
            // Http request
            HttpEntity<VChannel> request = new HttpEntity<>(vChannel);
            ResponseEntity<VChannel> response = restTemplate.exchange(uri, HttpMethod.POST, request, VChannel.class);
            VChannel createdChannel = response.getBody();
            return createdChannel;
        }
        catch(HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException();
        }
        // Catch connection exceptions
        catch(ResourceAccessException err){

            throw new VideoMinerConnectionRefusedException();
        }
    }

    public VChannel transformChannel(Channel channel){
        VChannel vChannel = new VChannel();
        vChannel.setId(channel.getId());
        vChannel.setName(channel.getName());
        vChannel.setDescription(channel.getDescription() == null? null:channel.getDescription().toString());
        vChannel.setCreatedTime(channel.getCreatedTime());
        vChannel.setVideos(new ArrayList<VVideo>());

        return vChannel;
    }



}
