package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
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
        assertEquals(channel.getId(), channelId);
    }

    @Test
    void getChannelNegative() {
        assertThrows(ChannelNotFoundException.class, ()->channelService.getChannel("foo"));
    }

    @Test
    void createChannel() throws ChannelNotFoundException, VideoMinerConnectionRefusedException {
        Channel channel = channelService.getChannel(channelId);
        VChannel channelRes = channelService.createChannel(channel);

        assertNotNull(channelRes);
        assertEquals(channelRes.getId(), channel.getId());
        assertEquals(channelRes.getName(), channel.getSnippet().getTitle());
        assertEquals(channelRes.getDescription(), channel.getSnippet().getDescription());
        assertEquals(channelRes.getCreatedTime(), channel.getSnippet().getPublishedAt());
    }
}
