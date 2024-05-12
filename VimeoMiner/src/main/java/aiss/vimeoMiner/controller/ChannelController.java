package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.*;
import aiss.vimeoMiner.service.*;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiBaseUri;


@Tag(name="Channel", description = "Channel manager API for Vimeo channels")
@RestController
@RequestMapping(apiBaseUri + "/channels")
public class ChannelController {

    @Autowired
    ChannelService channelService;
    @Autowired
    VideoService videoService;
    @Autowired
    VideoController videoController;

    @Operation(
            summary="Retrieve a channel",
            description = "Get a VChannel object with data retrieved from the channel with the given id in Vimeo",
            tags= {"channels", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
                @Schema(implementation=VChannel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode = "429", content = {@Content(schema=@Schema())})
    })
    @GetMapping("{channelId}")
    public VChannel findOne(@Parameter(description = "id of the channel to be searched") @PathVariable String channelId,
                            @Parameter(description = "maximum number of videos to retrieve from the channel") @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                            @Parameter(description = "maximum number of comments to retrieve from the videos in the channel") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws IncorrectMaxValueException, ChannelNotFoundException, VideoNotFoundException, CommentNotFoundException, CaptionNotFoundException, TooManyRequestsException {

        try{
            return populateChannel(channelId, maxVideos, maxComments);
        }
        catch(HttpClientErrorException.TooManyRequests err){
            throw new TooManyRequestsException();
        }
    }

    @Operation(
            summary="Create a channel",
            description = "Create a VChannel object in the VideoMiner database with data retrieved from the channel with the given id in Vimeo",
            tags= {"channels", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
                @Schema(implementation=VChannel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="408", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode = "429", content = {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{channelId}")
    public VChannel populateOne(@Parameter(description = "id of the channel to be searched") @PathVariable String channelId,
                                @Parameter(description = "maximum number of videos to retrieve from the channel") @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos,
                                @Parameter(description = "maximum number of comments to retrieve from the videos in the channel") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments)
            throws ChannelNotFoundException, VideoMinerConnectionRefusedException, VideoNotFoundException, CommentNotFoundException, CaptionNotFoundException, TooManyRequestsException, IncorrectMaxValueException {

        try{
            VChannel vChannel = populateChannel(channelId, maxVideos, maxComments);
            return channelService.createChannel(vChannel);
        }
        catch(HttpClientErrorException.TooManyRequests err){
            throw new TooManyRequestsException();
        }
    }

    private VChannel populateChannel(@PathVariable String channelId, @RequestParam(name = "maxVideos", defaultValue = "10") Integer maxVideos, @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments) throws ChannelNotFoundException, VideoNotFoundException, CaptionNotFoundException, CommentNotFoundException, IncorrectMaxValueException {
        Channel channel = channelService.getChannel(channelId);
        VChannel vChannel = channelService.transformChannel(channel);

        List<Video> videos = videoService.getVideos(channel.getMetadata().getConnections().getVideos().getUri(), maxVideos);
        for (Video v : videos) {
            VVideo vVideo = videoController.populateVVideo(v, maxComments);
            vChannel.getVideos().add(vVideo);
        }
        return vChannel;
    }


}
