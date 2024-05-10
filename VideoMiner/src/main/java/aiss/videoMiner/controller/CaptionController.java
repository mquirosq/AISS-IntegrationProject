package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CaptionNotFoundException;
import aiss.videoMiner.exception.VideoNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.CaptionRepository;
import aiss.videoMiner.repository.VideoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@RestController
@RequestMapping(apiBaseUri)
public class CaptionController {

    @Autowired
    CaptionRepository captionRepository;

    @Autowired
    VideoRepository videoRepository;

    @GetMapping("/captions")
    public List<Caption> findAll() { return captionRepository.findAll(); }

    @GetMapping("/captions/{captionId}")
    public Caption findOne(@PathVariable String captionId) throws CaptionNotFoundException {
        return captionRepository.findById(captionId).orElseThrow(CaptionNotFoundException::new);
    }

    @GetMapping("/videos/{videoId}/captions")
    public List<Caption> findByVideo(@PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new).getCaptions();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/videos/{videoId}/captions")
    public Caption create (@PathVariable String videoId, @Valid @RequestBody Caption caption) throws VideoNotFoundException{
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        List<Caption> captions = video.getCaptions();

        captions.add(caption);
        video.setCaptions(captions);

        videoRepository.save(video);
        return captionRepository.save(caption);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/captions/{captionId}")
    public void update(@Valid @RequestBody Caption updatedCaption, @PathVariable String captionId) throws CaptionNotFoundException {
        Optional<Caption> captionOptional = captionRepository.findById(captionId);

            if (!captionOptional.isPresent()) {
                throw new CaptionNotFoundException();
            }
            Caption caption = captionOptional.get();
            caption.setLanguage(updatedCaption.getLanguage());
            caption.setName(updatedCaption.getName());
            captionRepository.save(caption);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/captions/{captionId}")
    public void delete(@PathVariable String captionId){
        if (captionRepository.existsById(captionId)){
            captionRepository.deleteById(captionId);
        }
    }

}
