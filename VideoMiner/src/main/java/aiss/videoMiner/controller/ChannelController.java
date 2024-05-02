package aiss.videoMiner.controller;

import aiss.videoMiner.exception.ChannelNotFoundException;
import aiss.videoMiner.model.Channel;
import aiss.videoMiner.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videoMiner/v1/channels")
public class ChannelController {
    @Autowired
    ChannelRepository repository;

    // GET all
    @GetMapping
    public List<Channel> findAll(){
        return repository.findAll();
    }

    // GET data from a channel
    @GetMapping("/{channelId}")
    public Channel findOne(@PathVariable Long channelId) throws ChannelNotFoundException {
        Optional<Channel> channel = repository.findById(channelId);
        if (!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }

    // POST for Vimeo and YouTube Miners
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel){
        Channel _channel = repository
                .save(new Channel(channel.getId(), channel.getName(), channel.getDescription(), channel.getCreatedTime()));
        return _channel;
    }
}
