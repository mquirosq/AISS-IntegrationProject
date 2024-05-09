package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.CommentNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VUser;
import aiss.vimeoMiner.videoModel.VVideo;
import aiss.vimeoMiner.vimeoModel.modelComment.Comments;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import aiss.vimeoMiner.vimeoModel.modelVideos.Video;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getComments(String commentUri) throws CommentNotFoundException {
        // URI
        String uri = "https://api.vimeo.com" + commentUri;

        // Header for authentication
        HttpHeaders header = new HttpHeaders(){
            {
                String auth = "Bearer ee507ffdb4da956d56252e8eb067fb58";
                set("Authorization", auth);
            }
        };

        // Send message
        try {
            ResponseEntity<Comments> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Comments>(header), Comments.class);
            Comments comments = response.getBody();
            List<Comment> commentsArray = new ArrayList<>();
            if (comments != null){
                commentsArray = comments.getData();
            }

            String nextUrl = getNextPageUrl(response.getHeaders());
            while (nextUrl != null) {
                response = restTemplate.exchange(nextUrl, HttpMethod.GET, new HttpEntity<Comments>(header), Comments.class);
                comments = response.getBody();
                if (comments.getData() != null){
                    commentsArray.addAll(comments.getData());
                }
                nextUrl = getNextPageUrl(response.getHeaders());
            }
            return commentsArray;

        } catch (RestClientResponseException err){
            throw new CommentNotFoundException();
        }
    }

    //Post to VideoMiner
    public VComment createComment(Comment comment, String videoId) throws VideoMinerConnectionRefusedException, VideoNotFoundException {
        String uri = "http://localhost:8080/videoMiner/v1/videos/" + videoId + "/comments";
        try {
            // Convert properties:
            VComment vComment = transformComment(comment);
            // Http request
            HttpEntity<VComment> request = new HttpEntity<>(vComment);
            ResponseEntity<VComment> response = restTemplate.exchange(uri, HttpMethod.POST, request, VComment.class);
            return response.getBody();
        }
        catch(HttpClientErrorException.NotFound e) {
            throw new VideoNotFoundException();
        }
        // Catch connection exceptions
        catch(ResourceAccessException err){

            throw new VideoMinerConnectionRefusedException();
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

    public VComment transformComment(Comment comment){
        VComment vComment = new VComment();
        VUser vUser = new VUser();
        vUser.setName(comment.getUser().getName());
        vUser.setPicture_link(comment.getUser().getPictures().getUri().split("/")[4]);
        vUser.setUser_link(comment.getLink());
        vComment.setId(comment.getUri().split("/")[4]);
        vComment.setAuthor(vUser);
        vComment.setCreatedOn(comment.getCreatedOn());
        vComment.setText(comment.getText());
        return vComment;
    }
}

