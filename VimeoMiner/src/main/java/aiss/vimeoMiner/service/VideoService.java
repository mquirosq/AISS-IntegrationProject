package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import aiss.vimeoMiner.vimeoModel.modelVideos.Videos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static aiss.vimeoMiner.helper.AuthenticationHelper.createHttpHeaderAuthentication;
import static aiss.vimeoMiner.helper.ConstantsHelper.videoMinerBaseUri;
import static aiss.vimeoMiner.helper.ConstantsHelper.vimeoBaseUri;
import static aiss.vimeoMiner.helper.PaginationHelper.getNextPageUrl;
import static aiss.vimeoMiner.helper.PaginationHelper.getPageAndItemsPerPage;

@Service
public class VideoService {
    @Autowired
    RestTemplate restTemplate;
    private Pair<Integer, Integer> pageAndItemsPerPage;

    // Get from Vimeo API
    public List<Video> getVideos(String videosUri, Integer maxVideos) throws VideoNotFoundException {
        // Get pagination (max)
        Pair<Integer, Integer> pageAndItemsPerPage = getPageAndItemsPerPage(maxVideos);
        String paginationParams = pageAndItemsPerPage == null? "": "?page=" + pageAndItemsPerPage.getFirst() + "&per_page=" + pageAndItemsPerPage.getSecond();

        // URI
        String uri = vimeoBaseUri + videosUri + paginationParams;

        // Header for authentication
        HttpHeaders header = createHttpHeaderAuthentication();

        // Send message
        try {
            ResponseEntity<Videos> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Videos>(header), Videos.class);

            Videos videos = response.getBody();
            List<Video> videosArray = new ArrayList<>();
            if (videos != null) {
                videosArray = videos.getData();
            }
            return videosArray;
        }
        catch(HttpClientErrorException.NotFound err) {
            throw new VideoNotFoundException();
        }
    }

    public VVideo transformVideo(Video video){
        VVideo vVideo = new VVideo();
        vVideo.setId(video.getUri().split("/")[2]);
        vVideo.setName(video.getName());
        vVideo.setDescription(video.getDescription());
        vVideo.setComments(new ArrayList<>());
        vVideo.setCaptions(new ArrayList<>());
        vVideo.setReleaseTime(video.getReleaseTime());

        return vVideo;
    }
}
