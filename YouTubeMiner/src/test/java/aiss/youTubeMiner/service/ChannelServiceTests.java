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

}
