package aiss.videoMiner.controller;

import aiss.videoMiner.exception.ChannelNotFoundException;
import aiss.videoMiner.exception.VideoNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Channel;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.ChannelRepository;
import aiss.videoMiner.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@Tag(name="Video", description = "Video management API")
@RestController
@RequestMapping(apiBaseUri)
public class VideoController {

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    ChannelRepository channelRepository;

    @Operation(
            summary="Retrieve all Videos",
            description = "Get a list of Video objects including all the videos in the VideoMiner database",
            tags= {"videos", "get", "all"})
    @ApiResponse(responseCode = "200", content = {@Content(schema=
        @Schema(implementation= Video.class), mediaType="application/json")})
    @GetMapping("/videos")
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Operation(
            summary="Retrieve a Video by Id",
            description = "Get Video object by specifying its id",
            tags= {"videos", "get", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
                @Schema(implementation=Video.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}")
    public Video findOne(@Parameter(description = "id of the video to be searched") @PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
    }

    @Operation(
            summary="Retrieve all videos from a channel",
            description = "Get a list of Video objects belonging to the channel with the given id",
            tags= {"videos", "get", "id", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Video.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/channels/{channelId}/videos")
    public List<Video> findByChannel(@Parameter(description = "id of the channel from which to retrieve all videos") @PathVariable String channelId) throws ChannelNotFoundException {
        return channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new).getVideos();
    }

    @Operation(
            summary="Insert a Video in a channel",
            description = "Add a new Video whose data is passed in the body of the request in JSON format to the specified channel by id",
            tags= {"videos", "post", "id", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema=
            @Schema(implementation=Video.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels/{channelId}/videos")
    public Video create(@Parameter(description = "id of the channel the video will belong to") @PathVariable String channelId, @Valid @RequestBody Video video) throws ChannelNotFoundException {
        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);
        List<Video> videos = channel.getVideos();

        videos.add(video);
        channel.setVideos(videos);

        channelRepository.save(channel);
        return videoRepository.save(video);
    }

    @Operation(
            summary="Update a Video",
            description = "Update a Video object by specifying its id and whose data is passed in the body of the request in JSON format",
            tags= {"videos", "put", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/videos/{videoId}")
    public void update(@Valid @RequestBody Video updatedVideo, @Parameter(description = "id of the video to be updated") @PathVariable String videoId) throws VideoNotFoundException {
        Optional<Video> videoOptional = videoRepository.findById(videoId);

        if (!videoOptional.isPresent()) {
            throw new VideoNotFoundException();
        }
        Video video = videoOptional.get();
        video.setName(updatedVideo.getName());
        video.setDescription(updatedVideo.getDescription());
        video.setReleaseTime(updatedVideo.getReleaseTime());
        videoRepository.save(video);
    }

    @Operation(
            summary="Delete a Video",
            description = "Delete the Video identified by the given id",
            tags= {"videos", "delete", "id"})
    @ApiResponse(responseCode="204", content = {@Content(schema=@Schema())})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/videos/{videoId}")
    public void delete(@Parameter(description = "id of the video to be deleted") @PathVariable String videoId){
        if (videoRepository.existsById(videoId)){
            videoRepository.deleteById(videoId);
        }
    }

}
