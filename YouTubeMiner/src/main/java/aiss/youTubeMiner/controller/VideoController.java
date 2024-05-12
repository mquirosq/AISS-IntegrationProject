package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.service.VideoService;
import aiss.youTubeMiner.videoModel.VVideo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Video", description="Video management API using YouTube API")
@RestController
@RequestMapping(ConstantsHelper.apiBaseUri + "/channels")
public class VideoController {
    @Autowired
    VideoService videoService;

    @Operation(
            summary="Retrieve Videos from channel",
            description = "Get a List of VVideo objects belonging to the channel specified by id from YouTube",
            tags= {"videos", "get", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VVideo.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/{channelId}/videos")
    public List<VVideo> findAll(@Parameter(description = "id of the channel to which the videos belong") @PathVariable String channelId, @RequestParam(required = false) Integer maxVideos)
            throws VideoNotFoundException {
        return videoService.getVideos(channelId, (maxVideos == null) ? 10 : maxVideos, false).stream().map(video-> videoService.transformVideo(video)).toList();
    }
}
