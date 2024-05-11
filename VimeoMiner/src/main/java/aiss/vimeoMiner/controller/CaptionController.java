package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.CaptionNotFoundException;
import aiss.vimeoMiner.videoModel.VCaption;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiBaseUri;
import aiss.vimeoMiner.service.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Caption", description="Caption manager API for Vimeo captions")
@RestController
@RequestMapping(apiBaseUri)
public class CaptionController {

    @Autowired
    CaptionService captionService;


    @Operation(
            summary="Retrieve captions from video",
            description = "Get a List of VCaption objects belonging to the video specified by id from YouTube",
            tags= {"captions", "get", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VCaption.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}/captions")
    public List<VCaption> findAll(@Parameter(description = "id of the video to which the captions belong") @PathVariable String videoId) throws CaptionNotFoundException {
        List<Caption> captions = captionService.getCaptions("/videos/"+ videoId + "/texttracks");
        List<VCaption> vCaptions = new ArrayList<>();
        for (Caption caption : captions) {
            VCaption vCaption = captionService.transformCaption(caption);
            vCaptions.add(vCaption);
        }
        return vCaptions;
    }


}
