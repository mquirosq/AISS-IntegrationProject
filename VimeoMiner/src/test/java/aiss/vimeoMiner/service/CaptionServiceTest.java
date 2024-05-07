package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.CaptionNotFoundException;
import aiss.vimeoMiner.vimeoModel.modelCaption.Caption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CaptionServiceTest {

    @Autowired
    CaptionService service;

    @Test
    void getCaptions() throws CaptionNotFoundException {
        List<Caption> captions = service.getCaptions("/videos/301594884/texttracks");
        captions.forEach(c -> System.out.println(c.getLanguage()));
    }
}
