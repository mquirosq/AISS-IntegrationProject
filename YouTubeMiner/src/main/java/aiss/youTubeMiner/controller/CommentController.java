package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.service.CommentService;
import aiss.youTubeMiner.videoModel.VComment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Comment", description = "Comment management API using YouTube API")
@RestController
@RequestMapping(ConstantsHelper.apiBaseUri)
public class CommentController {

    @Autowired
    CommentService commentService;

    @Operation(
            summary="Retrieve Comments from video",
            description = "Get a List of Comment objects belonging to the video specified by id from YouTube",
            tags= {"comments", "get", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VComment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}/comments")
    public List<VComment> getCommentsFromVideo(@Parameter(description = "id of the video to which the comments belong") @PathVariable String videoId) throws VideoCommentsNotFoundException, CommentNotFoundException, OAuthException {
        return commentService.getCommentsFromVideo(videoId, 10, false).stream().map(x->commentService.transformComment(x)).toList();
    }

    @GetMapping("/comments/{commentId}")
    public VComment getComment(@PathVariable String commentId) throws CommentNotFoundException, OAuthException {
        return commentService.transformComment(commentService.getComment(commentId, false));
    }

}
