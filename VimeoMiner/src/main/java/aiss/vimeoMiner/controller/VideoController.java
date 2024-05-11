package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.CaptionNotFoundException;
import aiss.vimeoMiner.exception.CommentNotFoundException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.service.CaptionService;
import aiss.vimeoMiner.service.CommentService;
import aiss.vimeoMiner.service.VideoService;
import aiss.vimeoMiner.videoModel.VCaption;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiBaseUri;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Video", description = "Video management API using Vimeo API")
@RestController
@RequestMapping(apiBaseUri)
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    CaptionService captionService;
    @Autowired
    CommentService commentService;

    @Operation(
            summary="Retrieve videos from channel",
            description = "Get a List of VVideo objects belonging to the channel specified by id from YouTube",
            tags= {"videos", "get", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=VVideo.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/channels/{channelId}/videos")
    public List<VVideo> findAll(@Parameter(description = "id of the channel to which the videos belong") @PathVariable String channelId,
                                @Parameter(description = "maximum number of videos that will be retrieved") @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @Parameter(description = "maximum number of comments that will be retrieved for each video") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments) throws VideoNotFoundException, CaptionNotFoundException, CommentNotFoundException {
        List<Video> videos = videoService.getVideos("/channels/"+ channelId + "/videos", maxVideos);
        List<VVideo> vVideos = new ArrayList<>();
        for (Video v : videos) {
            VVideo vVideo = populateVVideo(v, maxComments);
            vVideos.add(vVideo);
        }
        return vVideos;
    }

    public VVideo populateVVideo(Video video, Integer maxComments) throws CaptionNotFoundException, CommentNotFoundException {
        VVideo vVideo = videoService.transformVideo(video);

        List<Caption> captions = captionService.getCaptions(video.getMetadata().getConnections().getTexttracks().getUri());
        for (Caption caption : captions) {
            VCaption vCaption = captionService.transformCaption(caption);
            vVideo.getCaptions().add(vCaption);
        }

        List<Comment> comments = commentService.getComments(video.getMetadata().getConnections().getComments().getUri(), maxComments);
        for (Comment comment : comments) {
            VComment vComment = commentService.transformComment(comment);

            vVideo.getComments().add(vComment);
        }
        return vVideo;
    }
}
