package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Video", description="Video management API using YouTube API")
@RestController
@RequestMapping(Constants.apiBase + "/channels")
public class VideoController {
    @Autowired
    VideoService videoService;

    @Operation(
            summary="Retrieve Videos from channel",
            description = "Get a List of Video objects belonging to the channel specified by id from YouTube",
            tags= {"videos", "get", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VVideo.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/{channelId}/videos")
    public List<VVideo> findAll(@Parameter(description = "id of the channel to which the videos belong") @PathVariable String channelId, @RequestParam(required = false) Integer max)
            throws VideoNotFoundException {
        return videoService.getVideos(channelId, (max == null) ? 10 : max).stream().map(x->videoService.transformVideo(x)).toList();
    }
}
