package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.CommentNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VUser;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService service;

    @Test
    void getComments() throws CommentNotFoundException {
        String videoId = "371426411";
        List<Comment> comments = service.getComments("/videos/371426411/comments", 10);
        comments.forEach(c -> assertEquals(videoId, c.getUri().split("/")[2],"Returned comment Uri should match given video ID"));
    }

    // Negative tests
    @Test
    void getCommentsNotFound(){
        String uri = "/videos/1/comments";
        assertThrows(CommentNotFoundException.class, () -> service.getComments(uri,10), "If the id does not correspond to a video a CommentNotFoundException should be raised");
    }

    // Create test
    @Test
    void createComment() throws VideoMinerConnectionRefusedException, CommentNotFoundException, VideoNotFoundException {
        List<Comment> comments = service.getComments("/videos/371426411/comments", 10);
        Comment comment = comments.get(0);
        VComment createdComment = service.createComment(comment,"371426411");
        VUser vUser = new VUser();
        vUser.setName(comment.getUser().getName());
        vUser.setPictureLink(comment.getUser().getPictures().getBaseLink());
        vUser.setUserLink(comment.getLink());

        assertNotNull(createdComment.getId(), "Created comment must have an id");
        assertEquals(comment.getUri().split("/")[4], createdComment.getId(), "Returned comment id should match given id");
        assertEquals(comment.getCreatedOn(), createdComment.getCreatedOn(), "Returned creation date name should match given creation date");
        assertEquals(comment.getText(), createdComment.getText(), "Returned comment text should match given comment text");
        assertEquals(vUser, createdComment.getAuthor(), "Returned comment author should match given author");
    }
}
