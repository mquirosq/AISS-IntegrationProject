package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.service.CaptionService;
import aiss.youTubeMiner.exception.CaptionNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Caption", description="Caption management API using YouTube API")
@RestController
@RequestMapping(Constants.apiBase + "/videos")
public class CaptionController {
    @Autowired
    CaptionService captionService;

    @Operation(
            summary="Retrieve a Captions from video",
            description = "Get a List of Caption objects belonging to the video specified by id from YouTube",
            tags= {"captions", "get", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=VCaption.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/{videoId}/captions")
    public List<VCaption> findAll(@Parameter(description = "id of the video to which the captions belong to") @PathVariable String videoId) throws CaptionNotFoundException {
        List<Caption> captions = captionService.getCaptions(videoId);
        List<VCaption> vCaptions = new ArrayList<>();
        for (Caption caption : captions){
            vCaptions.add(captionService.transformCaption(caption));
        }
        return vCaptions;
    }
}
