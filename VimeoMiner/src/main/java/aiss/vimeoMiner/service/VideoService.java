package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.ChannelNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class VideoService {
    @Autowired
    RestTemplate restTemplate;

    // Get from Vimeo API
    public List<Video> getVideos(String videosUri) throws VideoNotFoundException {
        // URI
        String uri = vimeoBaseUri + videosUri;

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

            String nextUrl = getNextPageUrl(response.getHeaders());
            while (nextUrl != null) {
                response = restTemplate.exchange(nextUrl, HttpMethod.GET, new HttpEntity<Videos>(header), Videos.class);
                videos = response.getBody();
                videosArray.addAll(videos.getData());
                nextUrl = getNextPageUrl(response.getHeaders());
            }
            return videosArray;
        }
        catch(RestClientResponseException err) {
            throw new VideoNotFoundException();
        }
    }

    // Post to VideoMiner:
    public VVideo createVideo(Video video, String channelId) throws VideoMinerConnectionRefusedException, ChannelNotFoundException {
        String uri = videoMinerBaseUri + "/channels/" + channelId + "/videos";
        try {
            // Convert properties:
            VVideo vVideo = transformVideo(video);
            // Http request
            HttpEntity<VVideo> request = new HttpEntity<>(vVideo);
            ResponseEntity<VVideo> response = restTemplate.exchange(uri, HttpMethod.POST, request, VVideo.class);
            VVideo createdVideo = response.getBody();
            return createdVideo;
        }
        catch(HttpClientErrorException.NotFound e) {
            throw new ChannelNotFoundException();
        }
        catch(ResourceAccessException err){
            // Catch connection exceptions
            throw new VideoMinerConnectionRefusedException();
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
