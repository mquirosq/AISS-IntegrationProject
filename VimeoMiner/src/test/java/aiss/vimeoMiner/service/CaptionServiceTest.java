package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.CaptionNotFoundException;
import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VCaption;
import aiss.vimeoMiner.videoModel.VChannel;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import aiss.vimeoMiner.vimeoModel.modelCaption.Captions;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CaptionServiceTest {

    @Autowired
    CaptionService service;

    // GET from Vimeo test
    @Test
    void getCaptions() throws CaptionNotFoundException {
        String videoId = "301594884";
        List<Caption> captions = service.getCaptions("/videos/301594884/texttracks");
        captions.forEach(c -> assertEquals(videoId, c.getUri().split("/")[2], "Returned caption Uri should match given video ID"));
    }

    // Negative tests
    @Test
    void getCaptionsNotFound(){
        String uri = "/videos/1/texttracks";
        assertThrows(CaptionNotFoundException.class, () -> service.getCaptions(uri), "If the id does not correspond to a video a CaptionNotFoundException should be raised");
    }

    // Create test
    @Test
    void createCaption() throws VideoMinerConnectionRefusedException, CaptionNotFoundException, VideoNotFoundException {
        List<Caption> captions = service.getCaptions("/videos/301594884/texttracks");
        Caption caption = captions.get(0);
        VCaption createdCaption = service.createCaption(caption,"1");
        assertNotNull(createdCaption.getId(), "Created comment must have an id");
        assertEquals(caption.getId().toString(), createdCaption.getId(), "Returned caption id should match given id");
        assertEquals(caption.getName(), createdCaption.getName(), "Returned caption name should match given name");
        assertEquals(caption.getLanguage(), createdCaption.getLanguage(), "Returned caption language should match given language");
    }


}
