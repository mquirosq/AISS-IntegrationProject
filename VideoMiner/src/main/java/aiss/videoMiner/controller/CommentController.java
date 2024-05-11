package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CommentNotFoundException;
import aiss.videoMiner.exception.UserNotFoundException;
import aiss.videoMiner.exception.VideoNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.CommentRepository;
import aiss.videoMiner.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@Tag(name="Comment", description="Comment management API")
@RestController
@RequestMapping(apiBaseUri)
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    UserController userController;

    @Operation(
            summary="Retrieve all Comments",
            description = "Get a list of Comment objects including all the comments in the VideoMiner database",
            tags= {"comments", "get", "all"})
    @ApiResponse(responseCode = "200", content = {@Content(schema=
    @Schema(implementation= Comment.class), mediaType="application/json")})
    @GetMapping("/comments")
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Operation(
            summary="Retrieve a Comment by Id",
            description = "Get a Comment object by specifying its id",
            tags= {"comments", "get", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Comment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/comments/{commentId}")
    public Comment findOne(@Parameter(description = "id of the comment to be searched")@PathVariable String commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Operation(
            summary="Retrieve all comments from a video",
            description = "Get a list of Comment objects belonging to the video with the given id",
            tags= {"comments", "get", "id", "video"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Comment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> findByVideo(@Parameter(description = "id of the video from which to retrieve the comments") @PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new).getComments();
    }

    @Operation(
            summary="Insert a Comment in a video",
            description = "Add a new Comment whose data is passed in the body of the request in JSON format to the specified video by id",
            tags= {"comments", "post", "id", "video"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema=
            @Schema(implementation=Comment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/videos/{videoId}/comments")
    public Comment create(@Parameter(description = "id of the video the comment will belong to")@PathVariable String videoId, @Valid @RequestBody Comment comment) throws VideoNotFoundException {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        List<Comment> comments = video.getComments();

        comments.add(comment);
        video.setComments(comments);

        videoRepository.save(video);
        return commentRepository.save(comment);
    }

    @Operation(
            summary="Update a Comment",
            description = "Update a Comment object by specifying its id and whose data is passed in the body of the request in JSON format",
            tags= {"comments", "put", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/comments/{commentId}")
    public void update(@Valid @RequestBody Comment updatedComment, @Parameter(description = "id of the comment to be modified")@PathVariable String commentId) throws CommentNotFoundException, UserNotFoundException {
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

    @Operation(
            summary="Delete a Comment",
            description = "Delete the Comment identified by the given id",
            tags= {"comments", "delete", "id"})
    @ApiResponse(responseCode="204", content = {@Content(schema=@Schema())})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void delete(@Parameter(description = "id of the comment to be deleted")@PathVariable String commentId){
        if (commentRepository.existsById(commentId)){
            commentRepository.deleteById(commentId);
        }
    }


}
