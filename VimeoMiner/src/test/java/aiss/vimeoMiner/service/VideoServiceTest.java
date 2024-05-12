package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.IncorrectMaxValueException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import aiss.vimeoMiner.vimeoModel.modelVideos.Videos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoServiceTest {

    @Autowired
    VideoService videoService;
    @Autowired
    ChannelService channelService;

    // GET from Vimeo tests:
    @Test
    void getVideos() throws VideoNotFoundException, IncorrectMaxValueException {
        Integer maxVideos = 2;
        List<Video> videos = videoService.getVideos("/channels/1470975/videos", maxVideos);
        assertEquals(maxVideos, videos.size(), "The video list should have the size of the maxVideos");
        videos.forEach(v -> {
            assertNotNull(v.getName(), "The name of the video should not be null");
        });
    }

    // Negative tests
    @Test
    void getVideoNotFound(){
        String uri = "/channels/1/videos";
        assertThrows(VideoNotFoundException.class, () -> videoService.getVideos(uri, 2), "If the id does not correspond to a video a VideoNotFoundException should be raised");
    }

}