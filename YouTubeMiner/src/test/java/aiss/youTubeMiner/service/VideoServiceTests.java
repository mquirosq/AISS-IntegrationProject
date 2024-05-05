package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.videoModel.VVideo;
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
        List<VideoSnippet> videos = videoService.getVideos(channelId);
        assertFalse(videos.isEmpty());
        videos.forEach(Assertions::assertNotNull);
    }

    @Test
    void getVideosNegative() {
        assertThrows(VideoNotFoundException.class, ()->videoService.getVideos("foo"));
    }

    @Test
    void createVideos() throws VideoNotFoundException, VideoMinerConnectionRefusedException {
        List<VideoSnippet> videos = videoService.getVideos(channelId);
        List<VVideo> videosRes = videos.stream().map(x-> {
            try {
                return videoService.createVideo(channelId, x);
            } catch (VideoMinerConnectionRefusedException e) {
                throw new RuntimeException(e);
            }
        })
        .toList();

        assertFalse(videosRes.isEmpty());

        for (int i = 0; i < videosRes.size(); i++) {
            assertNotNull(videosRes.get(i));
            assertEquals(videosRes.get(i).getId(), videos.get(i).getId().toString());
            assertEquals(videosRes.get(i).getName(), videos.get(i).getSnippet().getTitle());
            assertEquals(videosRes.get(i).getDescription(), videos.get(i).getSnippet().getDescription());
            assertEquals(videosRes.get(i).getReleaseTime(), videos.get(i).getSnippet().getPublishedAt());
        }
    }
}
