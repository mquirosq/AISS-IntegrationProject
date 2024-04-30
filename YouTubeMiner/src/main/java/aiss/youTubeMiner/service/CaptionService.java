package aiss.youTubeMiner.service;

import aiss.youTubeMiner.model.caption.Caption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptionService {
    @Autowired
    RestTemplate restTemplate;

    public Caption getCaption(String captionId) {
        return null;
    }
}
