package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.*;
import aiss.vimeoMiner.exception.UserNotFoundException;
import aiss.vimeoMiner.service.UserService;
import aiss.vimeoMiner.videoModel.VUser;
import aiss.vimeoMiner.vimeoModel.modelUser.ModelUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testGetUser() throws UserNotFoundException {
        String userURI = "/users/5241831";

        ModelUser user = userService.getUser(userURI);

        assertEquals(userURI, user.getUri(), "Returned user Uri should match given user Uri");
    }

   @Test
    public void testGetUserNotFound(){
        // Arrange
        String userURI = "/users/1";

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userURI), "If the id does not correspond to a user a UserNotFoundException should be raised");
    }
    @Test
    void createUser() throws VideoMinerConnectionRefusedException, UserNotFoundException, CommentNotFoundException {
        ModelUser modelUser=userService.getUser("/users/5241831");
        VUser createdUser = userService.createUser(modelUser);
        assertEquals(modelUser.getName(), createdUser.getName(), "Name of the user given should match the name of the user returned");
        assertEquals(modelUser.getLink(), createdUser.getUser_link(), "User link of the user given should match the user link of the user returned");
        assertEquals(modelUser.getPictures().getBaseLink(), createdUser.getPicture_link(), "Picture link of the user given should match the picture link of the user returned");
    }
}