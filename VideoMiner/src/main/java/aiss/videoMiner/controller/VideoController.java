package aiss.videoMiner.controller;

import aiss.videoMiner.exception.*;
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
import org.springframework.data.domain.*;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;
import static aiss.videoMiner.helper.PaginationHelper.*;

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
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Video.class), mediaType="application/json")}),
            @ApiResponse(responseCode="400", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos")
    public List<Video> findAll(@Parameter(description = "page to retrieve") @RequestParam(name = "offset", defaultValue = "0") int offset,
                               @Parameter(description = "maximum number of videos per page") @RequestParam(name = "limit", defaultValue = "10") int limit,
                               @Parameter(description = "string that must be included in the name of the video") @RequestParam(name="name", required = false) String name,
                               @Parameter(description = "takes as value one of the properties of the video and orders the videos by that parameter, ascending by default. To get the descending order add a - just before the name of the property") @RequestParam(name="orderBy", required = false) String orderBy) throws OrderByPropertyDoesNotExistVideoException, InvalidPageParametersException {

        if (limit <= 0 || offset < 0){
            throw new InvalidPageParametersException();
        }

        Pageable paging;

        if (orderBy != null){
            if (orderBy.startsWith("-")){
                paging = PageRequest.of(offset, limit, Sort.by(orderBy.substring(1)).descending());
            }
            else {
                paging = PageRequest.of(offset, limit, Sort.by(orderBy).ascending());
            }
        }
        else
            paging = PageRequest.of(offset, limit);

        Page<Video> pageVideos;

        try{
            if (name != null)
                pageVideos = videoRepository.findByNameContaining(name, paging);
            else
                pageVideos = videoRepository.findAll(paging);
        }
        catch(PropertyReferenceException err){
            throw new OrderByPropertyDoesNotExistVideoException();
        }
        return pageVideos.getContent();


    }

    @Operation(
            summary="Retrieve a Video by Id",
            description = "Get Video object by specifying its id",
            tags= {"videos", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
                @Schema(implementation=Video.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}")
    public Video findOne(@Parameter(description = "id of the video to be searched") @PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
    }

    @Operation(
            summary="Retrieve all videos from a channel",
            description = "Get a list of Video objects belonging to the channel with the given id",
            tags= {"videos", "get", "channels"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Video.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/channels/{channelId}/videos")
    public List<Video> findByChannel(@Parameter(description = "id of the channel from which to retrieve all videos") @PathVariable String channelId,
                                     @Parameter(description = "page to retrieve") @RequestParam(name = "offset", defaultValue = "0") int offset,
                                     @Parameter(description = "maximum number of videos per page") @RequestParam(name = "limit", defaultValue = "10") int limit,
                                     @Parameter(description = "string that must be included in the name of the video") @RequestParam(name="name", required = false) String name,
                                     @Parameter(description = "takes as value one of the properties of the video and orders the videos by that parameter, ascending by default. To get the descending order add a - just before the name of the property") @RequestParam(name="orderBy", required = false) String orderBy) throws ChannelNotFoundException, OrderByPropertyDoesNotExistVideoException, InvalidPageParametersException {

        List<Video> videos =  channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new).getVideos();

        if (name != null){
            videos = videos.stream().filter(video -> video.getName().contains(name)).toList();
        }

        Page<Video> pageVideo = getVideoPage(offset, limit, videos, orderBy);
        return pageVideo.getContent();
    }

    @Operation(
            summary="Insert a Video in a channel",
            description = "Add a new Video whose data is passed in the body of the request in JSON format to the specified channel by id",
            tags= {"videos", "post", "channels"})
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
            tags= {"videos", "put"})
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
            tags= {"videos", "delete"})
    @ApiResponse(responseCode="204", content = {@Content(schema=@Schema())})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/videos/{videoId}")
    public void delete(@Parameter(description = "id of the video to be deleted") @PathVariable String videoId){
        if (videoRepository.existsById(videoId)){
            videoRepository.deleteById(videoId);
        }
    }

}
