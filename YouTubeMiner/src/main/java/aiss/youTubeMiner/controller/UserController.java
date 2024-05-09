package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.service.CommentService;
import aiss.youTubeMiner.videoModel.VUser;
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

    @GetMapping("/comments/{commentId}/user")
    public VUser getUser(@PathVariable String commentId) throws CommentNotFoundException {
        return commentService.getUser(commentId);
    }
}
