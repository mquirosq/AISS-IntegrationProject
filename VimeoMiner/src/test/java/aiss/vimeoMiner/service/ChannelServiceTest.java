package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.videoModel.VChannel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelService service;

    // GET from Vimeo tests:
    @Test
    void getChannel() throws ChannelNotFoundException {
        String channelId = "1901688";
        Channel channel = service.getChannel(channelId);
        assertEquals(channelId, channel.getId(), "The id of the returned channel must match the id given");
    }

    // Negative test
    @Test
    void getChannelNotFound() {
        String channelId = "1";
        assertThrows(ChannelNotFoundException.class, () -> service.getChannel(channelId), "If the id does not correspond to a channel a ChannelNotFoundException should be raised");
    }

    // Create test:
    @Test
    void createChannel() throws VideoMinerConnectionRefusedException, ChannelNotFoundException {
        Channel channel = service.getChannel("1901688");
        VChannel createdChannel = service.createChannel(service.transformChannel(channel));
        assertNotNull(createdChannel.getId(), "Channel must have an id");
        assertEquals(channel.getId(), createdChannel.getId(), "Returned channel id should match given id");
        assertEquals(channel.getName(), createdChannel.getName(), "Returned channel name should match given name");
        assertEquals(channel.getDescription(), createdChannel.getDescription(), "Returned channel description should match given description");
        assertEquals(channel.getCreatedTime(), createdChannel.getCreatedTime(), "Returned channel created time should match given created time");
    }

}