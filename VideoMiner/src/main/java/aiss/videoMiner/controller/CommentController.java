package aiss.videoMiner.controller;

import aiss.videoMiner.exception.*;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.User;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.CommentRepository;
import aiss.videoMiner.repository.UserRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;
import static aiss.videoMiner.helper.PaginationHelper.getCommentPage;

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
    @Autowired
    UserRepository userRepository;

    @Operation(
            summary="Retrieve all Comments",
            description = "Get a list of Comment objects including all the comments in the VideoMiner database",
            tags= {"comments", "get", "all"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= Comment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="400", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/comments")
    public List<Comment> findAll(@Parameter(description = "page to retrieve") @RequestParam(name = "offset", defaultValue = "0") int offset,
                                 @Parameter(description = "maximum number of comments per page") @RequestParam(name = "limit", defaultValue = "10") int limit,
                                 @Parameter(description = "string contained in the text of the comment") @RequestParam(name="text", required = false) String text,
                                 @Parameter(description = "takes as value one of the properties of the comment and orders the comments by that parameter, ascending by default. To get the descending order add a - just before the name of the property") @RequestParam(name="orderBy", required = false) String orderBy)
            throws OrderByPropertyDoesNotExistCommentException, InvalidPageParametersException {

        if (limit <= 0 || offset < 0){
            throw new InvalidPageParametersException();
        }

        Pageable paging;

        if (orderBy != null){
            if (orderBy.startsWith("-")){
                paging = PageRequest.of(offset, limit, Sort.by(orderBy.substring(1)).descending());
            }
            else {
                paging = PageRequest.of(offset, limit, Sort.by(orderBy).ascending());
            }
        }
        else
            paging = PageRequest.of(offset, limit);

        Page<Comment> pageComments;

        try{
            if (text != null)
                pageComments = commentRepository.findByTextContaining(text, paging);
            else
                pageComments = commentRepository.findAll(paging);
        }
        catch(PropertyReferenceException err){
            throw new OrderByPropertyDoesNotExistCommentException();
        }
        return pageComments.getContent();
    }

    @Operation(
            summary="Retrieve a Comment by Id",
            description = "Get a Comment object by specifying its id",
            tags= {"comments", "get"})
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
            tags= {"comments", "get", "videos"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Comment.class), mediaType="application/json")}),
            @ApiResponse(responseCode="400", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> findByVideo(@Parameter(description = "id of the video from which to retrieve the comments") @PathVariable String videoId,
                                     @Parameter(description = "page to retrieve") @RequestParam(name = "offset", defaultValue = "0") int offset,
                                     @Parameter(description = "maximum number of comments per page") @RequestParam(name = "limit", defaultValue = "10") int limit,
                                     @Parameter(description = "string contained in the text of the comment") @RequestParam(name="text", required = false) String text,
                                     @Parameter(description = "takes as value one of the properties of the comment and orders the comments by that parameter, ascending by default. To get the descending order add a - just before the name of the property") @RequestParam(name="orderBy", required = false) String orderBy)
            throws OrderByPropertyDoesNotExistCommentException, VideoNotFoundException, InvalidPageParametersException {
        List<Comment> comments =  videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new).getComments();

        if (text != null){
            comments = comments.stream().filter(comment -> comment.getText().contains(text)).toList();
        }

        Page<Comment> pageComment = getCommentPage(offset, limit, comments, orderBy);
        return pageComment.getContent();
      }

    @Operation(
            summary="Insert a Comment in a video",
            description = "Add a new Comment whose data is passed in the body of the request in JSON format to the specified video by id",
            tags= {"comments", "post", "videos"})
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
            tags= {"comments", "put"})
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
            tags= {"comments", "delete"})
    @ApiResponse(responseCode="204", content = {@Content(schema=@Schema())})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{commentId}")
    public void delete(@Parameter(description = "id of the comment to be deleted")@PathVariable String commentId){
        if (commentRepository.existsById(commentId)){
            User user = commentRepository.findById(commentId).get().getAuthor();
            commentRepository.deleteById(commentId);
            userRepository.deleteById(user.getId());
        }
    }


}
