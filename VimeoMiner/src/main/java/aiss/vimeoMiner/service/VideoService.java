package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
import aiss.vimeoMiner.vimeoModel.modelChannel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import aiss.vimeoMiner.vimeoModel.modelVideos.Videos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VideoService {
    @Autowired
    RestTemplate restTemplate;

    // Get from Vimeo API
    public List<Video
            > getVideos(String videosUri){
        // URI
        String uri = "https://api.vimeo.com" + videosUri;

        // Header for authentication
        HttpHeaders header = new HttpHeaders(){
            {
                String auth = "Bearer ee507ffdb4da956d56252e8eb067fb58";
                set("Authorization", auth);
            }
        };

        // Send message
        ResponseEntity<Videos> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Videos>(header),Videos.class);
        Videos videos = response.getBody();
        List<Video> videosArray = new ArrayList<>();
        if (videos != null){
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

    // Post to VideoMiner:
    public Video createVideo(Video video){
        String uri = "localhost:8080/videoMiner/v1/channels/{channelId}/videos";
        try {
            HttpEntity<Video> request = new HttpEntity<>(video);
            ResponseEntity<Video> response = restTemplate.exchange(uri, HttpMethod.POST, request, Video.class);
            Video createdVideo = response.getBody();
            return createdVideo;
        }
        catch(RestClientResponseException err) {
            System.out.println("Error when creating the video " + video + ":"+ err.getLocalizedMessage());
            return null;
        }
    }



    // Get next page URL
    public static String getNextPageUrl(HttpHeaders headers) {
        String result = null;

        // If there is no link header, return null
        List<String> linkHeader = headers.get("Link");
        if (linkHeader == null)
            return null;

        // If the header contains no links, return null
        String links = linkHeader.get(0);
        if (links == null || links.isEmpty())
            return null;

        // Return the next page URL or null if none.
        for (String token : links.split(", ")) {
            if (token.endsWith("rel=\"next\"")) {
                // Found the next page. This should look something like
                // <https://api.github.com/repos?page=3&per_page=100>; rel="next"
                int idx = token.indexOf('>');
                result = token.substring(1, idx);
                break;
            }
        }

        return result;
    }
}
