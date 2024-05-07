package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.service.CaptionService;
import aiss.youTubeMiner.exception.CaptionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.apiBase + "/videos")
public class CaptionController {
    @Autowired
    CaptionService captionService;

    @GetMapping("/{videoId}/captions")
    public List<Caption> findAll(@PathVariable String videoId) throws CaptionNotFoundException {
        return captionService.getCaptions(videoId);
    }
}
