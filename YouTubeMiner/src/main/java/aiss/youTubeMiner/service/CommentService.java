package aiss.youTubeMiner.service;

import aiss.youTubeMiner.exception.CommentNotFoundException;
import aiss.youTubeMiner.exception.OAuthException;
import aiss.youTubeMiner.helper.ConstantsHelper;
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

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Authenticator authenticator;

    private String genVideoURI(String videoId, Integer maxComments, Boolean test) {
        String uri = ConstantsHelper.ytBaseUri + "/commentThreads";
        uri += ("?videoId=" + videoId);
        uri += ("&part=" + "snippet");
        uri += ("&maxResults=" + maxComments);

        if (test) {
            uri += ("&key=" + ConstantsHelper.apiKey);
        }
        return uri;
    }

    public List<Comment> getComments(String videoId, Integer maxComments) throws VideoCommentsNotFoundException, CommentNotFoundException {
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
        } catch (HttpClientErrorException.NotFound e) {
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
        out.setPictureLink(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl());
        out.setUserLink(comment.getCommentSnippet().getTopLevelComment().getSnippet().getAuthorChannelUrl());
        return out;
    }

}
