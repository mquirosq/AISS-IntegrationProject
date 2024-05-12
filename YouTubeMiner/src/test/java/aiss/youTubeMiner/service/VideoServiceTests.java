package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VideoServiceTests {
    @Autowired
    VideoService videoService;

    final String channelId = "UCAuUUnT6oDeKwE6v1NGQxug";

    @Test
    void getVideosPositive() throws VideoNotFoundException, OAuthException {
        List<VideoSnippet> videos = videoService.getVideos(channelId, 10, true);
        assertFalse(videos.isEmpty(), "Resulting video list cannot be empty.");
        videos.forEach(Assertions::assertNotNull);
    }

    @Test
    void getVideosNegative() {
        assertThrows(
                VideoNotFoundException.class,
                ()->videoService.getVideos("foo", 10, true),
                "Negative test must throw a VideoNotFoundException."
        );
    }
}
