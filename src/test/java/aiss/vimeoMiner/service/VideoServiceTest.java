package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import aiss.vimeoMiner.vimeoModel.modelVideos.Videos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoServiceTest {

    @Autowired
    VideoService service;
    @Test
    void getVideos() {
        List<Video> videos = service.getVideos("/users/9096387/videos");
        videos.forEach(v -> System.out.println(v.getName()));
    }

    @Test
    void getVideosBadRequest() {
        List<Video> videos = service.getVideos("/users/9/videos");
        videos.forEach(v -> System.out.println(v.getName()));
    }
}