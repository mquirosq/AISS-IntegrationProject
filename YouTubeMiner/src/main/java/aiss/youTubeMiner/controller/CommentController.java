package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VComment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import aiss.youTubeMiner.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "Comment management API using YouTube API")
@RestController
@RequestMapping(Constants.apiBase)
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
    public List<Comment> getCommentsFromVideo(@Parameter(description = "id of the video to which the comments belong") @PathVariable String videoId) throws VideoCommentsNotFoundException, CommentNotFoundException {
        return commentService.getCommentsFromVideo(videoId);
    }

    @GetMapping("/comments/{commentId}")
    public Comment getComment(@PathVariable String commentId) throws CommentNotFoundException {
        return commentService.getComment(commentId);
    }

}
