package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
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
    void getComments() throws VideoCommentsNotFoundException, CommentNotFoundException {
        List<Comment> comments = commentService.getComments(videoId, 10);
        assertFalse(comments.isEmpty(), "Resulting comment list must not be empty.");
        comments.forEach(x->assertNotNull(x, "Resulting comment cannot be null."));
    }

    @Test
    void getCommentsNotFound() {
        assertThrows(
                CommentNotFoundException.class,
                ()->commentService.getComments("everlong", 10),
                "Negative test must throw a CommentNotFoundException."
        );
    }
}
