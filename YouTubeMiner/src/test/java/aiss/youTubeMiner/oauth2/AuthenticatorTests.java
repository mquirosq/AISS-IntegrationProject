package aiss.youTubeMiner.oauth2;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.service.ChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthenticatorTests {
    @Autowired
    Authenticator authenticator;

    @Autowired
    ChannelService channelService;

    final String channelId = "UCtmftE9d_FKl3ZUzPaB1etw";

    @Test
    public void authNegative() throws ChannelNotFoundException, OAuthException {
        assertThrows(
                OAuthException.class,
                ()->channelService.getChannel(channelId, false),
                "Negative test should throw OAuthException."
        );
    }
}
