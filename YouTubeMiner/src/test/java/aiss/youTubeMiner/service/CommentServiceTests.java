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
    final String videoId = "jmycUQs6tVA";

    @Test
    void getCommentPositive() throws CommentNotFoundException {
        Comment comment = commentService.getComment(commentId);
        assertNotNull(comment);
    }

    @Test
    void getCommentNegative() {assertThrows(CommentNotFoundException.class, ()->commentService.getComment("fighters"));}

    @Test
    void createComment() throws VideoCommentsNotFoundException, CommentNotFoundException {
        List<Comment> comments = commentService.getCommentsFromVideo(videoId);
        List<VComment> commentsRes = comments.stream().map(x -> {
                    try {
                        return commentService.createComment(x, videoId);
                    } catch (VideoMinerConnectionRefusedException | VideoCommentsNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        assertFalse(commentsRes.isEmpty());

        for (int i = 0; i < commentsRes.size(); i++) {
            assertNotNull(commentsRes.get(i));
            assertEquals(commentsRes.get(i).getId(), comments.get(i).getCommentSnippet().getTopLevelComment().getId());
            assertEquals(commentsRes.get(i).getAuthor().getName(), comments.get(i).getCommentSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
            assertEquals(commentsRes.get(i).getCreatedOn(), comments.get(i).getCommentSnippet().getTopLevelComment().getSnippet().getPublishedAt());
            assertEquals(commentsRes.get(i).getText(), comments.get(i).getCommentSnippet().getTopLevelComment().getSnippet().getTextOriginal());
        }
    }

    @Test
    void getCommentsFromVideoPositive() throws VideoCommentsNotFoundException, CommentNotFoundException {
        List<Comment> comments = commentService.getCommentsFromVideo(videoId);
        assertFalse(comments.isEmpty());
        comments.forEach(Assertions::assertNotNull);
    }

    @Test
    void getCommentsFromVideoNegative() { assertThrows(CommentNotFoundException.class, ()-> commentService.getCommentsFromVideo("everlong"));}

    @Test
    void getUserPositive() throws CommentNotFoundException {
        VUser user = commentService.getUser(commentId);
        assertNotNull(user);
    }

    @Test
    void getUserNegative() { assertThrows(CommentNotFoundException.class, ()-> commentService.getUser("comment"));}

    @Test
    void createUser() throws CommentNotFoundException {
        VUser user = null;
        try {
            user = commentService.getUser(commentId);
        } catch (CommentNotFoundException e) {
            throw new RuntimeException();
        }

        assertNotNull(user);
    }
}
