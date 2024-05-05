package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.service.VideoService;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.service.ChannelService;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("youtubeMiner/api/v1/channels")
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @Autowired
    VideoService videoService;

    @GetMapping("{channelId}")
    public Channel findOne(@PathVariable String channelId) {
        return channelService.getChannel(channelId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId) throws VideoMinerConnectionRefusedException {
        Channel channel = findOne(channelId);
        VChannel out = channelService.createChannel(channel);

        videoService.getVideos(channelId).stream().forEach(x -> {
            try {
                out.getVideos().add(videoService.createVideo(out.getId(), x));
            } catch (VideoMinerConnectionRefusedException e) {
                throw new RuntimeException(e);
            }
        });
        return out;
    }
}
