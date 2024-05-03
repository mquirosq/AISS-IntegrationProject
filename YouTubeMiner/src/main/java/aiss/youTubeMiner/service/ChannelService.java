package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.youTubeModel.channel.ChannelSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    public RestTemplate restTemplate;

    final String key = "AIzaSyCgo33WDq8_uoH6tWH6COhTmemxQbimDHY";

    public Channel getChannel(String channelId) {
        String uri = "https://www.googleapis.com/youtube/v3/channels";
        uri += ("?id=" + channelId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + key);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ChannelSearch> request = new HttpEntity<>(headers);
        ResponseEntity<ChannelSearch> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                ChannelSearch.class
        );
        return response.getBody().getItems().get(0);
    }

    public VChannel createChannel(Channel channel) throws VideoMinerConnectionRefusedException {
        try {
            String uri = "http://localhost:8080/videoMiner/v1/channels";
            VChannel vChannel = mapChannel(channel);
            HttpEntity<VChannel> request = new HttpEntity<>(vChannel);
            ResponseEntity<VChannel> response = restTemplate.exchange(uri, HttpMethod.POST, request, VChannel.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            System.out.println("Error creating channel: " + e.getLocalizedMessage());
            return null;
        } catch (ResourceAccessException e) {
            throw new VideoMinerConnectionRefusedException();
        }
    }

    private VChannel mapChannel(Channel channel) {
        VChannel out = new VChannel();
        out.setId(channel.getId());
        out.setDescription(channel.getSnippet().getDescription());
        out.setName(channel.getSnippet().getTitle());
        out.setCreatedTime(channel.getSnippet().getPublishedAt());
        out.setVideos(new ArrayList<>());
        return out;
    }
}
