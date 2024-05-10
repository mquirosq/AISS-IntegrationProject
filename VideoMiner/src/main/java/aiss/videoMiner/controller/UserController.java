package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CaptionNotFoundException;
import aiss.videoMiner.exception.CommentNotFoundException;
import aiss.videoMiner.exception.UserNotFoundException;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.User;
import aiss.videoMiner.repository.CommentRepository;
import aiss.videoMiner.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@RestController
@RequestMapping(apiBaseUri)
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentController commentController;

    // Get All users
    @GetMapping("/users")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Get specific user by ID
    @GetMapping("/users/{userId}")
    public User findOne(@PathVariable Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new UserNotFoundException();
        }
        return user.get();
    }

    // Get user from a comment
    @GetMapping("/comments/{commentId}/user")
    public User findByComment(@PathVariable String commentId) throws CommentNotFoundException{
        Comment comment = commentController.findOne(commentId);
        return comment.getAuthor();
    }

    // POST for Vimeo and YouTube Miners
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comments/{commentId}/user")
    public User create(@Valid @RequestBody User user, @PathVariable String commentId) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.setAuthor(user);

        commentRepository.save(comment);
        return userRepository.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/users/{userId}")
    public void update(@Valid @RequestBody User updatedUser, @PathVariable Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        user.setName(updatedUser.getName());
        user.setUser_link(updatedUser.getUser_link());
        user.setPicture_link(updatedUser.getPicture_link());
        userRepository.save(user);
    }
}
