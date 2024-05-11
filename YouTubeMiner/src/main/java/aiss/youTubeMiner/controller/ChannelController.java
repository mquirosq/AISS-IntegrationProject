package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.Constants;
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
@RequestMapping(Constants.apiBase + "/channels")
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
    public VChannel findOne(@PathVariable String channelId) throws ChannelNotFoundException {
        return channelService.transformChannel(channelService.getChannel(channelId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId,
                                @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoNotFoundException, VideoMinerConnectionRefusedException {

        Channel channel = channelService.getChannel(channelId);
        VChannel out = channelService.createChannel(channel);

        if (maxVideos > 0) {
            videoService.getVideos(channelId, maxVideos).forEach(x -> {
                try {
                    out.getVideos().add(videoService.createVideo(out.getId(), x));
                } catch (ChannelNotFoundException|VideoMinerConnectionRefusedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if ( maxComments > 0) {
            out.getVideos().forEach(x -> {
                try {
                    commentService.getCommentsFromVideo(x.getId()).forEach(c -> {
                        try {
                            x.getComments().add(commentService.createComment(x.getId(), c));
                        } catch (VideoMinerConnectionRefusedException | VideoCommentsNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (VideoCommentsNotFoundException | CommentNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        out.getVideos().forEach( x -> {
            try {
                captionService.getCaptions(x.getId()).forEach(c -> {
                    try {
                        x.getCaptions().add(captionService.createCaption(x.getId(),c));
                    } catch (VideoNotFoundException|VideoMinerConnectionRefusedException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (CaptionNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return out;
    }
}
