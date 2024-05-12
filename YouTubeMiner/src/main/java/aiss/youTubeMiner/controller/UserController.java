package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.service.CommentService;
import aiss.youTubeMiner.videoModel.VUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.apiBase)
public class UserController {

    @Autowired
    CommentService commentService;

    @Operation(
            summary="Retrieve a User from comment",
            description = "Get a User object, the author of the comment specified by id from YouTube",
            tags= {"users", "get", "comments"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= VUser.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/comments/{commentId}/user")
    public VUser getUser(@Parameter(description = "id of the comment from which the user the author of") @PathVariable String commentId) throws CommentNotFoundException, OAuthException {
        return commentService.getUser(commentId);
    }
}
