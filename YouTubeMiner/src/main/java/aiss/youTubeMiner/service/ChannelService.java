package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.helper.Constants;
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

    public Channel getChannel(String channelId) throws ChannelNotFoundException {
        String uri = Constants.ytBase + "/channels";
        uri += ("?id=" + channelId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + Constants.apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ChannelSearch> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<ChannelSearch> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    ChannelSearch.class
            );
            return response.getBody().getItems().get(0);
        } catch (NullPointerException|RestClientResponseException e) {
            throw new ChannelNotFoundException();
        }
    }

    public VChannel createChannel(Channel channel) throws VideoMinerConnectionRefusedException {
        try {
            String uri = Constants.vmBase + "/channels";
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
