package aiss.youTubeMiner.service;

import aiss.youTubeMiner.model.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChannelService {
    @Autowired
    public RestTemplate restTemplate;

    public Channel getChannel(String channelId) {
        return null;
    }
}
