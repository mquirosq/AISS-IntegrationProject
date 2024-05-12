package aiss.videoMiner.controller;

import aiss.videoMiner.exception.*;
import aiss.videoMiner.model.Caption;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation= User.class), mediaType="application/json")}),
            @ApiResponse(responseCode="400", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/users")
    public List<User> findAll(@Parameter(description = "page to retrieve") @RequestParam(name = "offset", defaultValue = "0") int offset,
                              @Parameter(description = "maximum number of users per page") @RequestParam(name = "limit", defaultValue = "10") int limit,
                              @Parameter(description = "string corresponding to the name of the user") @RequestParam(name="name", required = false) String name,
                              @Parameter(description = "takes as value one of the properties of the user and orders the users by that parameter, ascending by default. To get the descending order add a - just before the name of the property") @RequestParam(name="orderBy", required = false) String orderBy)
            throws OrderByPropertyDoesNotExistUserException, InvalidPageParametersException {

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

        Page<User> pageCaptions;

        try{
            if (name != null)
                pageCaptions = userRepository.findByNameContaining(name, paging);
            else
                pageCaptions = userRepository.findAll(paging);
        }
        catch(PropertyReferenceException err){
            throw new OrderByPropertyDoesNotExistUserException();
        }
        return pageCaptions.getContent();
    }


    @Operation(
            summary="Retrieve a User by Id",
            description = "Get a User object by specifying its id",
            tags= {"users", "get"})
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
            tags= {"users", "get", "comments"})
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
            summary="Update a User",
            description = "Update a User object by specifying its id and whose data is passed in the body of the request in JSON format",
            tags= {"users", "put"})
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
