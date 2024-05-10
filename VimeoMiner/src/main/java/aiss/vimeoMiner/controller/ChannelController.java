package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.*;
import aiss.vimeoMiner.service.*;
import aiss.vimeoMiner.videoModel.VCaption;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiBaseUri;

@RestController
@RequestMapping(apiBaseUri + "/channels")
public class ChannelController {
    @Autowired
    ChannelService channelService;
    @Autowired
    VideoService videoService;
    @Autowired
    CaptionService captionService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @GetMapping("{channelId}")
    public VChannel findOne(@PathVariable String channelId) throws ChannelNotFoundException {
        try{
            Channel channel = channelService.getChannel(channelId);
            VChannel vChannel = channelService.transformChannel(channel);
            return vChannel;
        }
        catch(HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException();
        }
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId,
                                @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoMinerConnectionRefusedException, VideoNotFoundException, CommentNotFoundException, CaptionNotFoundException, TooManyRequestsException {

        try{
            Channel channel = channelService.getChannel(channelId);
            VChannel vChannel = channelService.transformChannel(channel);

            List<Video> videos = videoService.getVideos(channel.getMetadata().getConnections().getVideos().getUri(), maxVideos);
            for (Video v : videos) {
                VVideo vVideo = videoService.transformVideo(v);

                List<Caption> captions = captionService.getCaptions(v.getMetadata().getConnections().getTexttracks().getUri());
                for (Caption caption : captions) {
                    VCaption vCaption = captionService.transformCaption(caption);
                    vVideo.getCaptions().add(vCaption);
                }

                List<Comment> comments = commentService.getComments(v.getMetadata().getConnections().getComments().getUri(), maxComments);
                for (Comment comment : comments) {
                    VComment vComment = commentService.transformComment(comment);

                    vVideo.getComments().add(vComment);
                }
                vChannel.getVideos().add(vVideo);
            }
            VChannel createdChannel = channelService.createChannel(vChannel);
            return createdChannel;
        }
        catch(HttpClientErrorException.TooManyRequests err){
            throw new TooManyRequestsException();
        }
    }
}
