package aiss.vimeoMiner.service;

import aiss.vimeoMiner.exception.CommentNotFoundException;
import aiss.vimeoMiner.exception.VideoMinerConnectionRefusedException;
import aiss.vimeoMiner.exception.VideoNotFoundException;
import aiss.vimeoMiner.videoModel.VComment;
import aiss.vimeoMiner.videoModel.VUser;
import aiss.vimeoMiner.vimeoModel.modelComment.Comments;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
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

import static aiss.vimeoMiner.helper.AuthenticationHelper.createHttpHeaderAuthentication;
import static aiss.vimeoMiner.helper.ConstantsHelper.*;
import static aiss.vimeoMiner.helper.PaginationHelper.getNextPageUrl;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getComments(String commentUri) throws CommentNotFoundException {
        // URI
        String uri = vimeoBaseUri + commentUri;

        // Header for authentication
        HttpHeaders header = createHttpHeaderAuthentication();

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
        String uri = videoMinerBaseUri + "/videos/" + videoId + "/comments";
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

