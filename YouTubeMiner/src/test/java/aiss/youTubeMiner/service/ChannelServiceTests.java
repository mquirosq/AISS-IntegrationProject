package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChannelServiceTests {
    @Autowired
    ChannelService channelService;

    final String channelId = "UCAuUUnT6oDeKwE6v1NGQxug";

    @Test
    void getChannelPositive() throws ChannelNotFoundException {
        Channel channel = channelService.getChannel(channelId);
        assertEquals(channel.getId(), channelId, "Resulting channel is not the requested one.");
    }

    @Test
    void getChannelNegative() {
        assertThrows(
                ChannelNotFoundException.class,
                ()->channelService.getChannel("foo"),
            "Negative test must throw a ChannelNotFoundException."
        );
    }

    @Test
    void createChannel() throws ChannelNotFoundException, VideoMinerConnectionRefusedException, VideoNotFoundException {
        Channel channel = channelService.getChannel(channelId);
        VChannel channelRes = channelService.createChannel(channelService.transformChannel(channel));

        assertNotNull(channelRes, "Resulting channel cannot be null.");
        assertEquals(channelRes.getId(), channel.getId(), "Resulting channel ID does not equal requested one.");
        assertEquals(channelRes.getName(), channel.getSnippet().getTitle(), "Resulting channel name does not equal requested one.");
        assertEquals(channelRes.getDescription(), channel.getSnippet().getDescription(), "Requested channel description does not equal requested one.");
        assertEquals(channelRes.getCreatedTime(), channel.getSnippet().getPublishedAt(), "Requested channel creation time does not equal requested one.");
    }
}
