package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.*;
import aiss.vimeoMiner.service.CaptionService;
import aiss.vimeoMiner.service.CommentService;
import aiss.vimeoMiner.service.VideoService;
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
import aiss.vimeoMiner.service.ChannelService;

import java.util.List;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiBaseUri;

@RestController
@RequestMapping(apiBaseUri)
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


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@PathVariable String channelId,
                                @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoMinerConnectionRefusedException, VideoNotFoundException, CommentNotFoundException, CaptionNotFoundException {

        Channel channel = channelService.getChannel(String.valueOf(channelId));
        VChannel createdChannel = channelService.createChannel(channel);
        if (maxVideos > 0){
            List<Video> videos = videoService.getVideos(channel.getMetadata().getConnections().getVideos().getUri());
            for (Video v : videos.subList(0, Math.min(maxVideos, videos.size()))) {
                if (v != null) {
                    VVideo vVideo = videoService.createVideo(v, channelId);
                    createdChannel.getVideos().add(vVideo);

                    List<Caption> captions = captionService.getCaptions(v.getMetadata().getConnections().getTexttracks().getUri());
                    for (Caption caption : captions){
                        VCaption vCaption = captionService.createCaption(caption, vVideo.getId());
                        vVideo.getCaptions().add(vCaption);
                    }

                    List<Comment> comments = commentService.getComments(v.getMetadata().getConnections().getComments().getUri());
                    for (Comment comment : comments.subList(0, Math.min(maxComments, comments.size()))){
                        VComment vComment = commentService.createComment(comment, vVideo.getId());
                        vVideo.getComments().add(vComment);
                    }
                }
            }
        }
        return createdChannel;
    }
}
