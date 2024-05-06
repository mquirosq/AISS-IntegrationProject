package aiss.videoMiner.controller;

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

@RestController
@RequestMapping("/videoMiner/v1")
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
    @PostMapping("/users")
    public User create(@Valid @RequestBody User user){
        User _user = userRepository
                .save(new User(user.getName(), user.getUser_link(), user.getPicture_link()));
        return _user;
    }



}
