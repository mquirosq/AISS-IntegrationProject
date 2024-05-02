package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.model.channel.Channel;
import aiss.youTubeMiner.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("youtubeMiner/api/v1/channels")
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @GetMapping("{channelsId}")
    public List<Channel> get(@PathVariable String channelsId) {
        return channelService.getChannels(channelsId);
    }
}
