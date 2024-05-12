package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.videoModel.VComment;
import aiss.youTubeMiner.videoModel.VUser;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTests {

    @Autowired
    CommentService commentService;

    final String commentId = "Ugw1rC5jPweHXk7h7KR4AaABAg";

    final String videoId = "P9-fJI0iZv4";

    @Test
    void getCommentPositive() throws CommentNotFoundException {
        Comment comment = commentService.getComment(commentId);
        assertNotNull(comment, "Resulting comment cannot be null.");
    }

    @Test
    void getCommentNegative() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getComment("fighters"),
                "Negative test must throw a CommentNotFoundException."
        );
    }

    @Test
    void getCommentsFromVideoPositive() throws VideoCommentsNotFoundException, CommentNotFoundException {
        List<Comment> comments = commentService.getCommentsFromVideo(videoId, 10);
        assertFalse(comments.isEmpty(), "Resulting comment list must not be empty.");
        comments.forEach(x->assertNotNull(x, "Resulting comment cannot be null."));
    }

    @Test
    void getCommentsFromVideoNegative() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getCommentsFromVideo("everlong", 10),
                "Negative test must throw a CommentNotFoundException."
        );
    }

    @Test
    void getUserPositive() throws CommentNotFoundException {
        VUser user = commentService.getUser(commentId);
        assertNotNull(user, "Resulting user cannot be null.");
    }

    @Test
    void getUserNegative() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getUser("comment"),
                "Negative test must throw CommentNotFoundException."
        );
    }
}
