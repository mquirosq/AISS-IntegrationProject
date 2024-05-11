package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.*;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.service.CaptionService;
import aiss.youTubeMiner.service.CommentService;
import aiss.youTubeMiner.service.VideoService;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name="Channel", description="Channel management API using YouTube API")
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


    @Operation(
            summary="Retrieve a Channel by Id",
            description = "Get a Channel object given by its id from YouTube",
            tags= {"channels", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VChannel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("{channelId}")
    public Channel findOne(@Parameter(description = "id of the video to search for") @PathVariable String channelId) throws ChannelNotFoundException {
        return channelService.getChannel(channelId);
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
            throws ChannelNotFoundException, VideoNotFoundException, VideoMinerConnectionRefusedException {

        Channel channel = findOne(channelId);
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
                    commentService.getCommentsFromVideo(x.getId()).forEach( c -> {
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
