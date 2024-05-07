package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.UserNotFoundException;
import aiss.vimeoMiner.service.UserService;
import aiss.vimeoMiner.vimeoModel.modelUser.ModelUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testGetUser_Success() throws UserNotFoundException {
        // Arrange
        String userUri = "/users/5241831";
        String expectedUri = "https://api.vimeo.com" + userUri;





        ModelUser actualUser = userService.getUser(userUri);

        //Assert
        //assertEquals(userId, actualUser);
        //assertThrows(ChannelNotFoundException.class, () -> userService.getUser(userId));
    }

   @Test
    public void testGetUser_NotFound() {
        // Arrange
        String userId = "999999"; // Identyfikator użytkownika, który nie istnieje
        String expectedUri = "https://api.vimeo.com/users/" + userId;
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Authorization", "Bearer ee507ffdb4da956d56252e8eb067fb58");


        RestTemplate restTemplate = new RestTemplate();


        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }
}