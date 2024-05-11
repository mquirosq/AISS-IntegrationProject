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
        String videoId = "322774604";
        Integer maxComments = 3;
        List<Comment> comments = service.getComments("/videos/" + videoId + "/comments", maxComments);
        assertEquals(maxComments, comments.size(), "The comment list should have the size of the maxComments");
        comments.forEach(c -> assertEquals(videoId, c.getUri().split("/")[2],"Returned comment Uri should match given video ID"));
    }

    // Negative tests
    @Test
    void getCommentsNotFound(){
        String uri = "/videos/1/comments";
        assertThrows(CommentNotFoundException.class, () -> service.getComments(uri,10), "If the id does not correspond to a video a CommentNotFoundException should be raised");
    }

}
