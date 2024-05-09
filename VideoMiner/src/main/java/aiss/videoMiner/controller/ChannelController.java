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

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@RestController
@RequestMapping(apiBaseUri+ "/channels")
public class ChannelController {
    @Autowired
    ChannelRepository channelRepository;

    // GET all
    @GetMapping
    public List<Channel> findAll(){
        return channelRepository.findAll();
    }

    // GET data from a channel
    @GetMapping("/{channelId}")
    public Channel findOne(@PathVariable String channelId) throws ChannelNotFoundException {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if (!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }

    // POST for Vimeo and YouTube Miners
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel) {
        Channel _channel = channelRepository
                .save(channel);
        return _channel;
    }
}
