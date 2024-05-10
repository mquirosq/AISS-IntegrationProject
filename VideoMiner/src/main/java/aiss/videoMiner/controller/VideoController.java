package aiss.videoMiner.controller;

import aiss.videoMiner.exception.CaptionNotFoundException;
import aiss.videoMiner.exception.ChannelNotFoundException;
import aiss.videoMiner.exception.VideoNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Channel;
import aiss.videoMiner.model.Video;
import aiss.videoMiner.repository.ChannelRepository;
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
public class VideoController {
    @Autowired
    VideoRepository videoRepository;

    @Autowired
    ChannelRepository channelRepository;

    @GetMapping("/videos")
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @GetMapping("/videos/{videoId}")
    public Video findOne(@PathVariable String videoId) throws VideoNotFoundException {
        return videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
    }

    @GetMapping("/channels/{channelId}/videos")
    public List<Video> findByChannel(@PathVariable String channelId) throws ChannelNotFoundException {
        return channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new).getVideos();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/channels/{channelId}/videos")
    public Video create(@PathVariable String channelId, @Valid @RequestBody Video video) throws ChannelNotFoundException {
        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);
        List<Video> videos = channel.getVideos();

        videos.add(video);
        channel.setVideos(videos);

        channelRepository.save(channel);
        return videoRepository.save(video);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/videos/{videoId}")
    public void update(@Valid @RequestBody Video updatedVideo, @PathVariable String videoId) throws VideoNotFoundException {
        Optional<Video> videoOptional = videoRepository.findById(videoId);

        if (!videoOptional.isPresent()) {
            throw new VideoNotFoundException();
        }
        Video video = videoOptional.get();
        video.setName(updatedVideo.getName());
        video.setDescription(updatedVideo.getDescription());
        video.setReleaseTime(updatedVideo.getReleaseTime());
        videoRepository.save(video);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/videos/{videoId}")
    public void delete(@PathVariable String videoId){
        if (videoRepository.existsById(videoId)){
            videoRepository.deleteById(videoId);
        }
    }

}
