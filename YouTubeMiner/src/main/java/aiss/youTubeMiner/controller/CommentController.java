package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.videoModel.VUser;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import aiss.youTubeMiner.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("youtubeMiner/api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comments/{commentId}")
    public List<Comment> getComment(@PathVariable String commentId) throws CommentNotFoundException {
        return commentService.getComment(commentId);
    }

    @GetMapping("/comments/{commentId}/user")
    public VUser getUser(@PathVariable String commentId) throws CommentNotFoundException {
        return commentService.getUser(commentId);
    }

    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> getCommentsFromVideo(@PathVariable String videoId) throws VideoCommentsNotFoundException {
        return commentService.getCommentsFromVideo(videoId);
    }

}
