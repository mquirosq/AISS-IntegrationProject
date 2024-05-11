package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VideoServiceTests {
    @Autowired
    VideoService videoService;

    final String channelId = "UCAuUUnT6oDeKwE6v1NGQxug";

    @Test
    void getVideosPositive() throws VideoNotFoundException {
        List<VideoSnippet> videos = videoService.getVideos(channelId, 10);
        assertFalse(videos.isEmpty());
        videos.forEach(Assertions::assertNotNull);
    }

    @Test
    void getVideosNegative() {
        assertThrows(VideoNotFoundException.class, ()->videoService.getVideos("foo", 10));
    }
}
