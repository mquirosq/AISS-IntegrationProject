package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
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
    void getVideos() throws VideoNotFoundException {
        List<Video> videos = videoService.getVideos("/channels/1903455/videos", 10);
        assertNotEquals(new ArrayList<>(), videos);
        videos.forEach(v -> {
            assertNotNull(v.getName());
        });
    }
    // Negative test
    @Test
    void getChannelNotFound(){
        String uri = "/channels/1/videos";
        assertThrows(VideoNotFoundException.class, () -> videoService.getVideos(uri, 10));
    }

    // Create test:
    @Test
    void createVideo() throws ChannelNotFoundException, VideoNotFoundException, VideoMinerConnectionRefusedException {
        String channelId = "1901688";
        Channel channel = channelService.getChannel(channelId);
        List<Video> videoList = videoService.getVideos(channel.getMetadata().getConnections().getVideos().getUri(), 10);
        for (Video v : videoList){
            if (v != null) {
                VVideo vVideo = videoService.createVideo(v, channelId);
                assertNotNull(vVideo.getId());
                assertEquals(vVideo.getDescription(), v.getDescription());
                assertEquals(vVideo.getName(), v.getName());
                assertEquals(vVideo.getReleaseTime(), v.getReleaseTime());
            }
        }

    }


}