package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import aiss.vimeoMiner.service.ChannelService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {
    @Autowired
    ChannelService channelService;
    @GetMapping("{channelId}")
    public Channel findOne(@PathVariable Long channelId) {

        Channel channel = channelService.getChannel(String.valueOf(channelId));
        return channel;
    }
}
