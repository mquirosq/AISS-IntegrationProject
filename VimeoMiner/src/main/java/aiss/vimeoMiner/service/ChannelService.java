package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.ArrayList;

import static aiss.vimeoMiner.helper.AuthenticationHelper.createHttpHeaderAuthentication;
import static aiss.vimeoMiner.helper.ConstantsHelper.videoMinerBaseUri;
import static aiss.vimeoMiner.helper.ConstantsHelper.vimeoBaseUri;


@Service
public class ChannelService {
    @Autowired
    RestTemplate restTemplate;

    // Get from Vimeo API
    public Channel getChannel(String channelId) throws ChannelNotFoundException {
        // URI
        String uri = vimeoBaseUri + "/channels/" + channelId;

        // Header for authentication
        HttpHeaders header = createHttpHeaderAuthentication();

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
    public VChannel createChannel(VChannel channel) throws VideoMinerConnectionRefusedException, ChannelNotFoundException {
        String uri = videoMinerBaseUri + "/channels";
        try {
            // Http request
            HttpEntity<VChannel> request = new HttpEntity<>(channel);
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
        vChannel.setVideos(new ArrayList<>());

        return vChannel;
    }



}
