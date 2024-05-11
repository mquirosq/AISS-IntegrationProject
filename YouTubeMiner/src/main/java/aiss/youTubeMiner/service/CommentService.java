package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.VideoCommentsNotFoundException;
import aiss.youTubeMiner.exception.VideoMinerConnectionRefusedException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.videoModel.VComment;
import aiss.youTubeMiner.videoModel.VUser;
import aiss.youTubeMiner.youTubeModel.comment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    public VUser getUser(String commentsId) throws CommentNotFoundException {
        try {
            Comment cm = getComment(commentsId);
            return transformUser(cm);
        } catch (CommentNotFoundException e) {
            throw new CommentNotFoundException();
        }
    }

    public Comment getComment(String commentsId) throws CommentNotFoundException {
        List<Comment> aux = new ArrayList<>();
        Comment out = new Comment();

        String uri = Constants.ytBaseUri + "/commentThreads";
        uri += ("?id=" + commentsId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + Constants.apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CommentSearch> request = new HttpEntity<>(headers);

        ResponseEntity<CommentSearch> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                CommentSearch.class
        );

        try {
            if (response.getBody() != null) {
                aux.addAll(response.getBody().getItems());
                out = Objects.requireNonNull(aux.stream().findFirst().orElse(null));
            }

            String next = getNextPage(uri, response);

            while (next != null) {
                response = restTemplate.exchange(next, HttpMethod.GET, null, CommentSearch.class);

                if (response.getBody() != null) {
                    aux.addAll(response.getBody().getItems());
                    out = Objects.requireNonNull(aux.stream().findFirst().orElse(null));
                }
                next = getNextPage(uri, response);
            }
        } catch (RestClientResponseException | NullPointerException e) {
            throw new CommentNotFoundException();
        }
        return out;
    }

    public List<Comment> getCommentsFromVideo(String videoId) throws VideoCommentsNotFoundException, CommentNotFoundException {
        List<Comment> out = new ArrayList<>();

        String uri = Constants.ytBaseUri + "/commentThreads";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");
        uri += ("&key=" + Constants.apiKey);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CommentSearch> request = new HttpEntity<CommentSearch>(headers);

        ResponseEntity<CommentSearch> response = null;
        try {
            response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    CommentSearch.class
            );
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().value() == 403) {
                out = new ArrayList<>();
            } else if (e.getStatusCode().value() == 404) {
                throw new CommentNotFoundException();
            }
        }

        try {
            if (response != null && response.getBody() != null) {
                out.addAll(response.getBody().getItems());
            }

            String next = null;
            if (response != null) {
                next = getNextPage(uri, response);
            }

            while (next != null) {
                response = restTemplate.exchange(next, HttpMethod.GET, null, CommentSearch.class);

                if (response.getBody() != null) {
                    out.addAll(response.getBody().getItems());
                }
                next = getNextPage(uri, response);
            }
        } catch (RestClientResponseException | CommentNotFoundException e) {
            throw new CommentNotFoundException();
        }
        return out;
    }

    public String getNextPage(String uri, ResponseEntity<CommentSearch> response) throws CommentNotFoundException {
        String next = null;

        try {
            next = response.getBody().getNextPageToken();
        } catch (NullPointerException e) {
            throw new CommentNotFoundException();
        }

        if (next == null) {
            return null;
        }
        return uri + ("&pageToken=" + next);
    }

    public VComment createComment(String videoId, Comment comment) throws VideoMinerConnectionRefusedException, VideoCommentsNotFoundException {
        try {
            String uri = Constants.vmBaseUri + "/videos/" + videoId + "/comments";
            VComment vComment = transformComment(comment);
            HttpEntity<VComment> request = new HttpEntity<>(vComment);
            ResponseEntity<VComment> response = restTemplate.exchange(uri, HttpMethod.POST, request, VComment.class);
            return response.getBody();
        } catch(HttpClientErrorException.NotFound e) {
            throw new VideoCommentsNotFoundException();
        } catch (ResourceAccessException e) {
            throw new VideoMinerConnectionRefusedException();
        }
    }
    
    public VUser createUser(String commentId) throws VideoMinerConnectionRefusedException, CommentNotFoundException {
        try {
            String uri = Constants.vmBaseUri + "/comments/" + commentId + "/user";
            VUser vUser = getUser(commentId);
            HttpEntity<VUser> request = new HttpEntity<>(vUser);
            ResponseEntity<VUser> response = restTemplate.exchange(uri, HttpMethod.POST, request, VUser.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            throw new VideoMinerConnectionRefusedException();
        } catch(HttpClientErrorException.NotFound e) {
            throw new CommentNotFoundException();
        } catch (CommentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public VComment transformComment(Comment comment) {
        VComment out = new VComment();
        VUser aux = transformUser(comment);
        out.setId(comment.getCommentSnippet().getTopLevelComment().getId());
        out.setAuthor(aux);
        out.setText(comment.getCommentSnippet().getTopLevelComment().getSnippet().getTextOriginal());
        out.setCreatedOn(comment.getCommentSnippet().getTopLevelComment().getSnippet().getPublishedAt());
        return out;
    }

    public VUser transformUser(Comment comment) {
        VUser out = new VUser();
        out.setName(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
        out.setPicture_link(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
        out.setUser_link(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorChannelUrl());
        return out;
    }

}
