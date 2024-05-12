package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.service.CaptionService;
import aiss.youTubeMiner.service.CommentService;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VComment;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.service.VideoService;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Video", description="Video management API using YouTube API")
@RestController
@RequestMapping(ConstantsHelper.apiBaseUri + "/channels")
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    CaptionService captionService;
    @Autowired
    CommentService commentService;

    @Operation(
            summary="Retrieve Videos from channel",
            description = "Get a List of VVideo objects belonging to the channel specified by id from YouTube",
            tags= {"videos", "get", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VVideo.class), mediaType="application/json")}),
            @ApiResponse(responseCode="401", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/{channelId}/videos")
    public List<VVideo> findAll(@Parameter(description = "id of the channel to which the videos belong") @PathVariable String channelId,
                                @RequestParam(required = false) Integer maxVideos,
                                @Parameter(description = "maximum number of comments that will be retrieved for each video") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws VideoNotFoundException, OAuthException, CaptionNotFoundException, VideoCommentsNotFoundException, CommentNotFoundException {
        List<VideoSnippet> videos = videoService.getVideos(channelId, (maxVideos == null) ? 10 : maxVideos, false);
        List<VVideo> vVideos = new ArrayList<>();
        for (VideoSnippet video : videos){
            VVideo vVideo = videoService.populateVVideo(video, maxComments);
            vVideos.add(vVideo);
        }
        return vVideos;
    }
}
