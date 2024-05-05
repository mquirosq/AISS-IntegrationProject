package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.service.CaptionService;
import aiss.youTubeMiner.service.CommentService;
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

    @Autowired
    CommentService commentService;

    @Autowired
    CaptionService captionService;

    @GetMapping("{channelId}")
    public Channel findOne(@PathVariable String channelId) throws ChannelNotFoundException {
        return channelService.getChannel(channelId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId,
                                @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoNotFoundException, VideoMinerConnectionRefusedException {

        Channel channel = findOne(channelId);
        VChannel out = channelService.createChannel(channel);

        if (maxVideos > 0) {
            videoService.getVideos(channelId).subList(0,maxVideos).forEach(x -> {
                try {
                    out.getVideos().add(videoService.createVideo(out.getId(), x));
                } catch (VideoMinerConnectionRefusedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if ( maxComments > 0) {
            out.getVideos().forEach(x -> {
                try {
                    commentService.getCommentsFromVideo(x.getId()).forEach( c -> {
                        try {
                            x.getComments().add(commentService.createComment(c, x.getId()));
                        } catch (VideoMinerConnectionRefusedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (VideoCommentsNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        out.getVideos().forEach( x -> {
            captionService.getCaptions(x.getId()).forEach( c -> {
                try {
                    x.getCaptions().add(captionService.createCaption(x.getId(),c));
                } catch (VideoMinerConnectionRefusedException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        return out;
    }
}
