package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CommentNotFoundException;
import aiss.videoMiner.exception.UserNotFoundException;
import aiss.videoMiner.exception.VideoNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.CommentRepository;
import aiss.videoMiner.repository.VideoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@RestController
@RequestMapping(apiBaseUri)
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    VideoRepository videoRepository;
    @Autowired
    UserController userController;

    @GetMapping("/comments")
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @GetMapping("/comments/{commentId}")
    public Comment findOne(@PathVariable String commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> findByVideo(@PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new).getComments();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/videos/{videoId}/comments")
    public Comment create(@PathVariable String videoId, @Valid @RequestBody Comment comment) throws VideoNotFoundException {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        List<Comment> comments = video.getComments();

        comments.add(comment);
        video.setComments(comments);

        videoRepository.save(video);
        return commentRepository.save(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/comments/{commentId}")
    public void update(@Valid @RequestBody Comment updatedComment, @PathVariable String commentId) throws CommentNotFoundException, UserNotFoundException {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);

        if (!commentOptional.isPresent()) {
            throw new CommentNotFoundException();
        }
        Comment comment = commentOptional.get();
        comment.setCreatedOn(updatedComment.getCreatedOn());
        comment.setText(updatedComment.getText());
        userController.update(updatedComment.getAuthor(), comment.getAuthor().getId());
        commentRepository.save(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable String commentId){
        if (commentRepository.existsById(commentId)){
            commentRepository.deleteById(commentId);
        }
    }


}
