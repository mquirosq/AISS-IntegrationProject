package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CaptionNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.exception.VideoNotFoundException;
import aiss.youTubeMiner.videoModel.VCaption;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
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

    final String videoId = "Ks-_Mh1QhMc";

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
    void createCaptions() throws CaptionNotFoundException {
        List<Caption> captions = captionService.getCaptions(videoId);
        List<VCaption> captionsRes = captions.stream().map(x-> {
            try {
                return captionService.createCaption(videoId, x);
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
