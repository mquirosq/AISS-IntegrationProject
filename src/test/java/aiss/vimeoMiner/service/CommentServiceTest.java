package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService service;

    @Test
    void getComments(){
        List<Comment> comments = service.getComments("/videos/371426411/comments");
        comments.forEach(c -> System.out.println(c.getText()));
    }
}
