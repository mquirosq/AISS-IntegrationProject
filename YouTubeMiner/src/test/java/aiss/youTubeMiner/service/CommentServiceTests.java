package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.videoModel.VUser;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
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
    void getCommentPositive() throws CommentNotFoundException, OAuthException {
        Comment comment = commentService.getComment(commentId, true);
        assertNotNull(comment, "Resulting comment cannot be null.");
    }

    @Test
    void getCommentNegative() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getComment("fighters", true),
                "Negative test must throw a CommentNotFoundException."
        );
    }

    @Test
    void getCommentsFromVideoPositive() throws VideoCommentsNotFoundException, CommentNotFoundException, OAuthException {
        List<Comment> comments = commentService.getCommentsFromVideo(videoId, 10, true);
        assertFalse(comments.isEmpty(), "Resulting comment list must not be empty.");
        comments.forEach(x->assertNotNull(x, "Resulting comment cannot be null."));
    }

    @Test
    void getCommentsFromVideoNegative() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getCommentsFromVideo("everlong", 10, true),
                "Negative test must throw a CommentNotFoundException."
        );
    }

    @Test
    void getUserPositive() throws CommentNotFoundException, OAuthException {
        VUser user = commentService.getUser(commentId, true);
        assertNotNull(user, "Resulting user cannot be null.");
    }

    @Test
    void getUserNegative() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getUser("comment", true),
                "Negative test must throw CommentNotFoundException."
        );
    }
}
