package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.helper.Constants;
import aiss.youTubeMiner.oauth2.Authenticator;
import aiss.youTubeMiner.videoModel.VComment;
import aiss.youTubeMiner.videoModel.VUser;
import aiss.youTubeMiner.youTubeModel.comment.Comment;
import aiss.youTubeMiner.youTubeModel.comment.CommentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Authenticator authenticator;

    private String genVideoURI(String videoId, Integer maxComments, Boolean test) {
        String uri = Constants.ytBase + "/commentThreads";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");
        uri += ("&maxResults=" + maxComments);

        if (test) {
            uri += ("&key=" + Constants.apiKey);
        }
        return uri;
    }

    public VUser getUser(String commentsId, Boolean test) throws CommentNotFoundException, OAuthException {
        try {
            Comment cm = getComment(commentsId, test);
            return transformUser(cm);
        } catch (CommentNotFoundException e) {
            throw new CommentNotFoundException();
        }
    }

    public Comment getComment(String commentsId, Boolean test) throws CommentNotFoundException, OAuthException {
        List<Comment> aux = new ArrayList<>();
        Comment out = new Comment();

        String uri = Constants.ytBase + "/commentThreads";
        uri += ("?id=" + commentsId);
        uri += ("&part=" + "snippet");

        HttpHeaders headers = null;

        if (test) {
            uri += ("&key=" + Constants.apiKey);
        } else {
            headers = authenticator.getAuthHeader();
        }
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
                response = restTemplate.exchange(next, HttpMethod.GET, request, CommentSearch.class);

                if (response.getBody() != null) {
                    aux.addAll(response.getBody().getItems());
                    out = Objects.requireNonNull(aux.stream().findFirst().orElse(null));
                }
                next = getNextPage(uri, response);
            }
        } catch (RestClientResponseException|NullPointerException e) {
            throw new CommentNotFoundException();
        }
        return out;
    }

    public List<Comment> getCommentsFromVideo(String videoId, Integer maxComments, Boolean test) throws CommentNotFoundException, OAuthException {
        List<Comment> out = new ArrayList<>();
        String next = null;

        HttpHeaders headers = null;

        if (!test) {
            headers = authenticator.getAuthHeader();
        }
        HttpEntity<CommentSearch> request = new HttpEntity<CommentSearch>(headers);
        ResponseEntity<CommentSearch> response = null;

        try {
            response = restTemplate.exchange(
                    genVideoURI(videoId, maxComments, test),
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
            next = getNextPage(genVideoURI(videoId, (maxComments - out.size()), test), response);

            while (out.size() < maxComments && next != null) {
                response = restTemplate.exchange(next, HttpMethod.GET, request, CommentSearch.class);

                if (response.getBody() != null) {
                    out.addAll(response.getBody().getItems());
                }
                next = getNextPage(genVideoURI(videoId, maxComments - out.size(), test), response);
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

    public VComment transformComment(Comment comment) {
        VComment out = new VComment();
        VUser aux = transformUser(comment);
        out.setId(comment.getCommentSnippet().getTopLevelComment().getId());
        out.setAuthor(aux);
        out.setText(comment.getCommentSnippet().getTopLevelComment().getSnippet().getTextOriginal());
        out.setCreatedOn(comment.getCommentSnippet().getTopLevelComment().getSnippet().getPublishedAt());
        return out;
    }

    private VUser transformUser(Comment comment) {
        VUser out = new VUser();
        out.setName(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
        out.setPicture_link(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
        out.setUser_link(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorChannelUrl());
        return out;
    }

}
