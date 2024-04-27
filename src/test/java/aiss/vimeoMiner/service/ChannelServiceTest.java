package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelService service;

    @Test
    void getChannel() {
        Channel channel = service.getChannel("1901688");
        System.out.println(channel.getName());
    }
}