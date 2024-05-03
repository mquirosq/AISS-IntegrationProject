package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("youtubeMiner/api/v1/videos")
public class VideoController {
    @Autowired
    VideoService videoService;

    @GetMapping("{channelId}")
    public List<VideoSnippet> get(@PathVariable String channelId) {
        return videoService.getVideos(channelId);
    }
}
