package aiss.vimeoMiner.controller;

import aiss.vimeoMiner.exception.CaptionNotFoundException;
import aiss.vimeoMiner.exception.CommentNotFoundException;
import aiss.vimeoMiner.service.CommentService;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiBaseUri;

@Tag(name="Comment", description="Comment manager API for Vimeo comments")
@RestController
@RequestMapping(apiBaseUri)
public class CommentController {

    @Autowired
    CommentService commentService;

    @Operation(
            summary="Retrieve comments from video",
            description = "Get a List of VComment objects belonging to the video specified by id from Vimeo",
            tags= {"comments", "get", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VComment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}/comments")
    public List<VComment> findAll(@Parameter(description = "id of the video to which the comments belong") @PathVariable String videoId,
    @Parameter(description = "maximum number of comments that will be retrieved for each video") @RequestParam(name = "maxComments", defaultValue = "10") Integer maxComments) throws CaptionNotFoundException, CommentNotFoundException {
        List<Comment> comments = commentService.getComments("/videos/"+ videoId + "/comments", maxComments);
        List<VComment> vComments = new ArrayList<>();
        for (Comment comment : comments) {
            VComment vComment = commentService.transformComment(comment);
            vComments.add(vComment);
        }
        return vComments;
    }
}
