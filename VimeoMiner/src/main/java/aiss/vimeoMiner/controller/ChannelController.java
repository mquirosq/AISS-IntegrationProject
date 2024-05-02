package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.service.CaptionService;
import aiss.vimeoMiner.service.CommentService;
import aiss.vimeoMiner.service.VideoService;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import aiss.vimeoMiner.service.ChannelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vimeoMiner/v1/channels")
public class ChannelController {
    @Autowired
    ChannelService channelService;
    @Autowired
    VideoService videoService;
    @Autowired
    CaptionService captionService;
    @Autowired
    CommentService commentService;

    @GetMapping("{channelId}")
    public Channel findOne(@PathVariable Long channelId) throws ChannelNotFoundException {
        try{
            Channel channel = channelService.getChannel(String.valueOf(channelId));
            return channel;
        }
        catch (Exception err){
            throw err;
        }
    }

    // TODO: Make it so you can populate with a list of IDs?
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public Channel populateOne(@PathVariable Long channelId) throws ChannelNotFoundException {

        Channel channel = channelService.getChannel(String.valueOf(channelId));
        Channel createdChannel = channelService.createChannel(channel);
        List<Video> videoList = videoService.getVideos(channel.getMetadata().getConnections().getVideos().getUri());
        for (Video v : videoList){
            System.out.println(v.toString());
            // videoService.createVideo(v);
        }

        return createdChannel;
    }
}
