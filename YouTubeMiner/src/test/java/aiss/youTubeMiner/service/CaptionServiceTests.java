package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CaptionNotFoundException;
import aiss.youTubeMiner.exception.ChannelNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.videoModel.VChannel;
import aiss.youTubeMiner.videoModel.VVideo;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import aiss.youTubeMiner.youTubeModel.channel.Channel;
import aiss.youTubeMiner.youTubeModel.videoSnippet.VideoSnippet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CaptionServiceTests {
    @Autowired
    CaptionService captionService;

    @Autowired
    VideoService videoService;

    @Autowired
    ChannelService channelService;

    final String videoId = "Ks-_Mh1QhMc";
    final String channelId = "UCAuUUnT6oDeKwE6v1NGQxug";

    @Test
    void getCaptionsPositive() throws CaptionNotFoundException {
        List<Caption> captions = captionService.getCaptions(videoId);
        assertFalse(captions.isEmpty());
        captions.forEach(Assertions::assertNotNull);
    }

    @Test
    void getCaptionsNegative() {
        assertThrows(CaptionNotFoundException.class, ()->captionService.getCaptions("foo"));
    }

    @Test
    void createCaptions() throws CaptionNotFoundException, ChannelNotFoundException,
                                VideoMinerConnectionRefusedException, VideoNotFoundException {
        Channel channel = channelService.getChannel(channelId);
        channelService.createChannel(channel);

        List<VideoSnippet> videos = videoService.getVideos(channelId, 1);
        VVideo video = videoService.createVideo(channelId, videos.get(0));

        List<Caption> captions = captionService.getCaptions(videos.get(0).getId().getVideoId());
        List<VCaption> captionsRes = captions.stream().map(x-> {
            try {
                return captionService.createCaption(videos.get(0).getId().getVideoId(), x);
            } catch (VideoNotFoundException|VideoMinerConnectionRefusedException e) {
                throw new RuntimeException(e);
            }
        })
        .toList();
        assertFalse(captionsRes.isEmpty());

        for (int i = 0; i < captionsRes.size(); i++) {
            assertNotNull(captionsRes.get(i));
            assertEquals(captionsRes.get(i).getId(), captions.get(i).getId());
            assertEquals(captionsRes.get(i).getName(), captions.get(i).getSnippet().getName());
            assertEquals(captionsRes.get(i).getLanguage(), captions.get(i).getSnippet().getLanguage());
        }
    }
}
