package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.service.CaptionService;
import aiss.vimeoMiner.service.CommentService;
import aiss.vimeoMiner.service.VideoService;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import aiss.vimeoMiner.service.ChannelService;

import java.util.List;

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
    public VChannel findOne(@PathVariable Long channelId) throws ChannelNotFoundException {
        try{
            Channel channel = channelService.getChannel(String.valueOf(channelId));
            VChannel vChannel = channelService.transformChannel(channel);
            return vChannel;
        }
        catch (Exception err){
            throw err;
        }
    }

    // TODO: Make it so you can populate with a list of IDs?
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId,
                                @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoMinerConnectionRefusedException, VideoNotFoundException {

        Channel channel = channelService.getChannel(String.valueOf(channelId));
        VChannel createdChannel = channelService.createChannel(channel);
        if (maxVideos > 0){
            List<Video> videoList = videoService.getVideos(channel.getMetadata().getConnections().getVideos().getUri());
            for (Video v : videoList.subList(0, Math.min(maxVideos, videoList.size()))) {
                if (v != null) {
                    VVideo vVideo = videoService.createVideo(v, channelId);
                    createdChannel.getVideos().add(vVideo);
                }
                // TODO: Implement max Comments
            }
        }
        return createdChannel;
    }
}
