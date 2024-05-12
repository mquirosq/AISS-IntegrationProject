package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.youTubeModel.channel.ChannelSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    VideoService videoService;

    public Channel getChannel(String channelId) throws ChannelNotFoundException {
        String uri = ConstantsHelper.ytBaseUri + "/channels";
        uri += ("?id=" + channelId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + ConstantsHelper.apiKey);

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
        } catch (NullPointerException|HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException();
        }
    }

    public VChannel createChannel(VChannel vChannel) throws VideoMinerConnectionRefusedException, ChannelNotFoundException {
        try {
            String uri = ConstantsHelper.vmBaseUri + "/channels";
            HttpEntity<VChannel> request = new HttpEntity<>(vChannel);
            ResponseEntity<VChannel> response = restTemplate.exchange(uri, HttpMethod.POST, request, VChannel.class);
            return response.getBody();
        } catch(HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException();
        } catch (ResourceAccessException e) {
            throw new VideoMinerConnectionRefusedException();
        }
    }

    public VChannel transformChannel(Channel channel){
        VChannel out = new VChannel();
        out.setId(channel.getId());
        out.setDescription(channel.getSnippet().getDescription());
        out.setName(channel.getSnippet().getTitle());
        out.setCreatedTime(channel.getSnippet().getPublishedAt());
        out.setVideos(new ArrayList<>());
        return out;
    }
}
