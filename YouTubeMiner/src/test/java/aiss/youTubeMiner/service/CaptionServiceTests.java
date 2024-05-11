package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CaptionNotFoundException;
import aiss.youTubeMiner.youTubeModel.caption.Caption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}
