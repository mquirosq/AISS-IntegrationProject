package aiss.youTubeMiner.service;

import aiss.youTubeMiner.model.channel.Channel;
import aiss.youTubeMiner.model.channel.ChannelSearch;
import aiss.youTubeMiner.model.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.model.videoSnippet.VideoSnippetSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    public RestTemplate restTemplate;

    final String key = "AIzaSyCgo33WDq8_uoH6tWH6COhTmemxQbimDHY";

    public List<Channel> getChannels(String channelsId) {
        List<Channel> out = new ArrayList<>();

        String uri = "https://www.googleapis.com/youtube/v3/channels";
        uri += ("?id=" + channelsId);
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

        if (response.getBody() != null) {
            out.addAll(response.getBody().getItems());
        }
        return out;
    }
}
