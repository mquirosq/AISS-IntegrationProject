package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VComment;
import aiss.youTubeMiner.videoModel.VUser;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import aiss.youTubeMiner.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.apiBase)
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comments/{commentId}")
    public VComment getComment(@PathVariable String commentId) throws CommentNotFoundException {
        return commentService.transformComment(commentService.getComment(commentId));
    }

    @GetMapping("/videos/{videoId}/comments")
    public List<VComment> getCommentsFromVideo(@PathVariable String videoId) throws VideoCommentsNotFoundException, CommentNotFoundException {
        return commentService.getCommentsFromVideo(videoId).stream().map(x->commentService.transformComment(x)).toList();
    }

}
