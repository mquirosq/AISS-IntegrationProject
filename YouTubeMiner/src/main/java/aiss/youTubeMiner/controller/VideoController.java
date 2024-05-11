package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import aiss.youTubeMiner.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.apiBase + "/channels")
public class VideoController {
    @Autowired
    VideoService videoService;

    @GetMapping("/{channelId}/videos")
    public List<VVideo> findAll(@PathVariable String channelId, @RequestParam(required = false) Integer max)
            throws VideoNotFoundException {
        return videoService.getVideos(channelId, (max == null) ? 10 : max).stream().map(x -> {
            try {
                return videoService.createVideo(channelId, x);
            } catch (ChannelNotFoundException|VideoMinerConnectionRefusedException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
