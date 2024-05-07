package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.UserNotFoundException;
import aiss.vimeoMiner.exception.UserNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
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
    public void testGetUser_Success() throws UserNotFoundException {
        // Arrange
        String userURI = "/users/5241831";

        ModelUser actualUser = userService.getUser(userURI);

        //Assert
        assertEquals(userURI, actualUser.getUri());
    }

   @Test
    public void testGetUser_NotFound() throws UserNotFoundException{
        // Arrange
        String userURI = "/users/1";

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userURI));
    }
    @Test
    void createUser() throws VideoMinerConnectionRefusedException, UserNotFoundException{
        ModelUser modelUser=userService.getUser("/users/5241831");
        VUser createdUser = userService.createUser(modelUser);
        assertNotNull(createdUser.getName());
        assertEquals(modelUser.getName(), createdUser.getName());
        assertEquals(modelUser.getLink(), createdUser.getUser_link());
        assertEquals(modelUser.getPictures().getBaseLink(), createdUser.getPicture_link());

    }
}