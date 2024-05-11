package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.service.CaptionService;
import aiss.youTubeMiner.service.CommentService;
import aiss.youTubeMiner.service.VideoService;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.videoModel.VComment;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.service.ChannelService;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
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
    public Channel findOne(@PathVariable String channelId) throws ChannelNotFoundException {
        return channelService.getChannel(channelId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId,
                                @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoNotFoundException, VideoMinerConnectionRefusedException, CaptionNotFoundException, VideoCommentsNotFoundException, CommentNotFoundException {

        Channel channel = channelService.getChannel(channelId);
        VChannel vChannel = channelService.createChannel(channel);

        List<VideoSnippet> videos = videoService.getVideos(channelId, maxVideos);
        for (VideoSnippet v : videos) {
            VVideo vVideo = videoService.createVideo(channelId, v);
            String videoId = v.getId().getVideoId();

            List<Caption> captions = captionService.getCaptions(videoId);
            for (Caption caption : captions) {
                VCaption vCaption = captionService.createCaption(videoId, caption);
                vVideo.getCaptions().add(vCaption);
            }

            List<Comment> comments = commentService.getCommentsFromVideo(videoId, maxComments);
            for (Comment comment : comments) {
                VComment vComment = commentService.createComment(videoId, comment);
                vVideo.getComments().add(vComment);
            }
            vChannel.getVideos().add(vVideo);
        }
        return vChannel;
    }
}
