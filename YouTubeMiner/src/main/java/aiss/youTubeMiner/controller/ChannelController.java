package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.ConstantsHelper;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Channel", description="Channel management API using YouTube API")
@RestController
@RequestMapping(ConstantsHelper.apiBaseUri + "/channels")
public class ChannelController {

    @Autowired
    ChannelService channelService;
    @Autowired
    VideoService videoService;
    @Autowired
    CommentService commentService;
    @Autowired
    CaptionService captionService;

    @Operation(
            summary="Retrieve a Channel by Id",
            description = "Get a Channel object given by its id from YouTube",
            tags= {"channels", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VChannel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="401", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("{channelId}")
    public VChannel findOne(@Parameter(description = "id of the video to search for") @PathVariable String channelId,
                           @Parameter(description = "maximum number of videos to retrieve from the channel") @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                           @Parameter(description = "maximum number of comments to retrieve from the videos in the channel") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, OAuthException, VideoNotFoundException, CaptionNotFoundException, VideoCommentsNotFoundException, CommentNotFoundException {

        Channel channel = channelService.getChannel(channelId, false);
        VChannel vChannel = channelService.transformChannel(channel);

        List<VideoSnippet> videos = videoService.getVideos(channelId, maxVideos, false);
        for (VideoSnippet v : videos) {
            VVideo vVideo = videoService.populateVVideo(v, maxComments);
            vChannel.getVideos().add(vVideo);
        }

        return vChannel;
    }

    @Operation(
            summary="Create a channel",
            description = "Create a VChannel object in the VideoMiner database with data retrieved from the channel with the given id in YouTube",
            tags= {"channels", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=VChannel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="408", content = {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@Parameter(description = "id of the channel to be searched") @PathVariable String channelId,
                                @Parameter(description = "maximum number of videos to retrieve from the channel") @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @Parameter(description = "maximum number of comments to retrieve from the videos in the channel") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoNotFoundException, VideoMinerConnectionRefusedException, VideoCommentsNotFoundException, CommentNotFoundException, CaptionNotFoundException, OAuthException {

        Channel channel = channelService.getChannel(channelId, false);
        VChannel vChannel = channelService.transformChannel(channel);

        List<VideoSnippet> videos = videoService.getVideos(channelId, maxVideos, false);
        for (VideoSnippet v : videos) {
            VVideo vVideo = videoService.populateVVideo(v, maxComments);
            vChannel.getVideos().add(vVideo);
        }
        return channelService.createChannel(vChannel);
    }
}
