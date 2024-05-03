package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.service.CaptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("youtubeMiner/api/v1/captions")
public class CaptionController {
    @Autowired
    CaptionService captionService;

    @GetMapping("{videoId}")
    public List<Caption> get(@PathVariable String videoId) {
        return captionService.getCaptions(videoId);
    }
}
