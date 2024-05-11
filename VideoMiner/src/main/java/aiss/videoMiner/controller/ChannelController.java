package aiss.videoMiner.controller;

import aiss.videoMiner.exception.ChannelNotFoundException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Channel;
import aiss.videoMiner.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static aiss.videoMiner.helper.ConstantsHelper.apiBaseUri;

@Tag(name="Channel", description="Channel management API")
@RestController
@RequestMapping(apiBaseUri+ "/channels")
public class ChannelController {
    @Autowired
    ChannelRepository channelRepository;

    @Operation(
            summary="Retrieve all Channels",
            description = "Get a list of Channel objects including all the channels in the VideoMiner database",
            tags= {"channels", "get", "all"})
    @ApiResponse(responseCode = "200", content = {@Content(schema=
    @Schema(implementation=Channel.class), mediaType="application/json")})
    @GetMapping
    public List<Channel> findAll(){
        return channelRepository.findAll();
    }

    @Operation(
            summary="Retrieve a Channel by Id",
            description = "Get a Channel object by specifying its id",
            tags= {"channels", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(implementation=Channel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())})
    })
    @GetMapping("/{channelId}")
    public Channel findOne(@Parameter(description = "id of the channel to be searched")@PathVariable String channelId) throws ChannelNotFoundException {
        Optional<Channel> channel = channelRepository.findById(channelId);
        if (!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }

    @Operation(
            summary="Insert a Channel",
            description = "Add a new Channel whose data is passed in the body of the request in JSON format",
            tags= {"channels", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema=
            @Schema(implementation=Channel.class), mediaType="application/json")}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel) {
        Channel _channel = channelRepository
                .save(channel);
        return _channel;
    }

    @Operation(
            summary="Update a Channel",
            description = "Update a Channel object by specifying its id and whose data is passed in the body of the request in JSON format",
            tags= {"channels", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="404", content = {@Content(schema=@Schema())}),
            @ApiResponse(responseCode="400", content= {@Content(schema=@Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{channelId}")
    public void update(@Valid @RequestBody Channel updatedChannel, @Parameter(description = "id of the channel to be updated")@PathVariable String channelId) throws ChannelNotFoundException {
        Optional<Channel> channelOptional = channelRepository.findById(channelId);

        if (!channelOptional.isPresent()) {
            throw new ChannelNotFoundException();
        }
        Channel channel = channelOptional.get();
        channel.setName(updatedChannel.getName());
        channel.setDescription(updatedChannel.getDescription());
        channel.setCreatedTime(updatedChannel.getCreatedTime());
        channelRepository.save(channel);
    }

    @Operation(
            summary="Delete a Channel",
            description = "Delete the Channel identified by the given id",
            tags= {"channels", "delete"})
    @ApiResponse(responseCode="204", content = {@Content(schema=@Schema())})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{channelId}")
    public void delete(@Parameter(description = "id of the channel to be deleted") @PathVariable String channelId){
        if (channelRepository.existsById(channelId)){
            channelRepository.deleteById(channelId);
        }
    }

}
