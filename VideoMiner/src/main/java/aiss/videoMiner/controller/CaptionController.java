package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CaptionNotFoundException;
import aiss.videoMiner.exception.VideoNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.CaptionRepository;
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

@Tag(name="Caption", description="Caption management API")
@RestController
@RequestMapping(apiBaseUri)
public class CaptionController {

    @Autowired
    CaptionRepository captionRepository;
    @Autowired
    VideoRepository videoRepository;

    @Operation(
            summary="Retrieve all Captions",
            description = "Get a list of Caption objects including all the captions in the VideoMiner database",
            tags= {"captions", "get", "all"})
    @ApiResponse(responseCode = "200", content = {@Content(schema=
        @Schema(implementation=Caption.class), mediaType="application/json")})
    @GetMapping("/captions")
    public List<Caption> findAll() { return captionRepository.findAll(); }

    @Operation(
            summary="Retrieve a Caption by Id",
            description = "Get a Caption object by specifying its id",
            tags= {"captions", "get", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
                @Schema(implementation=Caption.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/captions/{captionId}")
    public Caption findOne(@Parameter(description = "id of the caption to be searched") @PathVariable String captionId) throws CaptionNotFoundException {
        return captionRepository.findById(captionId).orElseThrow(CaptionNotFoundException::new);
    }

    @Operation(
            summary="Retrieve all captions from a video",
            description = "Get a list of Caption objects belonging to the video with the given id",
            tags= {"captions", "get", "id", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Caption.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}/captions")
    public List<Caption> findByVideo(@Parameter(description="id of the video from which to retrieve the captions")@PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new).getCaptions();
    }

    @Operation(
            summary="Insert a Caption in a video",
            description = "Add a new Caption whose data is passed in the body of the request in JSON format to the specified video by id",
            tags= {"captions", "post", "id", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema=
            @Schema(implementation=Caption.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/videos/{videoId}/captions")
    public Caption create (@Parameter(description="id of the video the caption will belong to") @PathVariable String videoId, @Valid @RequestBody Caption caption) throws VideoNotFoundException{
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        List<Caption> captions = video.getCaptions();

        captions.add(caption);
        video.setCaptions(captions);

        videoRepository.save(video);
        return captionRepository.save(caption);
    }

    @Operation(
            summary="Update a Caption",
            description = "Update a Caption object by specifying its id and whose data is passed in the body of the request in JSON format",
            tags= {"captions", "put", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/captions/{captionId}")
    public void update(@Valid @RequestBody Caption updatedCaption, @Parameter(description = "id of the caption to be updated") @PathVariable String captionId) throws CaptionNotFoundException {
        Optional<Caption> captionOptional = captionRepository.findById(captionId);

            if (!captionOptional.isPresent()) {
                throw new CaptionNotFoundException();
            }
            Caption caption = captionOptional.get();
            caption.setLanguage(updatedCaption.getLanguage());
            caption.setName(updatedCaption.getName());
            captionRepository.save(caption);
    }

    @Operation(
            summary="Delete a Caption",
            description = "Delete the Caption identified by the given id",
            tags= {"captions", "delete", "id"})
    @ApiResponse(responseCode="204", content = {@Content(schema=@Schema())})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/captions/{captionId}")
    public void delete(@Parameter(description = "id of the caption to be deleted") @PathVariable String captionId){
        if (captionRepository.existsById(captionId)){
            captionRepository.deleteById(captionId);
        }
    }

}
