package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CommentNotFoundException;
import aiss.videoMiner.exception.UserNotFoundException;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.User;
import aiss.videoMiner.repository.CommentRepository;
import aiss.videoMiner.repository.UserRepository;
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

@Tag(name="User", description = "User management API")
@RestController
@RequestMapping(apiBaseUri)
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentController commentController;

    @Operation(
            summary="Retrieve all Users",
            description = "Get a list of User objects including all the users in the VideoMiner database",
            tags= {"users", "get", "all"})
    @ApiResponse(responseCode = "200", content = {@Content(schema=
        @Schema(implementation= User.class), mediaType="application/json")})
    @GetMapping("/users")
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Operation(
            summary="Retrieve a User by Id",
            description = "Get a User object by specifying its id",
            tags= {"users", "get", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=User.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/users/{userId}")
    public User findOne(@Parameter(description = "id of the user to be searched") @PathVariable Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Operation(
            summary="Retrieve the author from a comment",
            description = "Get a User object representing the author of the comment with the given id",
            tags= {"users", "get", "id", "comments"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=User.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/comments/{commentId}/user")
    public User findByComment(@Parameter(description = "id of the comment from which to retrieve the author")@PathVariable String commentId) throws CommentNotFoundException{
        Comment comment = commentController.findOne(commentId);
        return comment.getAuthor();
    }

    @Operation(
            summary="Insert a User in a comment",
            description = "Add a new User whose data is passed in the body of the request in JSON format to the specified comment by id",
            tags= {"users", "post", "id", "comments"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema=
            @Schema(implementation=User.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comments/{commentId}/user")
    public User create(@Valid @RequestBody User user, @Parameter(description = "id of the comment the user will belong to") @PathVariable String commentId) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.setAuthor(user);

        commentRepository.save(comment);
        return userRepository.save(user);
    }

    @Operation(
            summary="Update a User",
            description = "Update a User object by specifying its id and whose data is passed in the body of the request in JSON format",
            tags= {"users", "put", "id"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/users/{userId}")
    public void update(@Valid @RequestBody User updatedUser, @Parameter(description = "id of the comment to be updated")@PathVariable Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        user.setName(updatedUser.getName());
        user.setUserLink(updatedUser.getUserLink());
        user.setPictureLink(updatedUser.getPictureLink());
        userRepository.save(user);
    }
}
