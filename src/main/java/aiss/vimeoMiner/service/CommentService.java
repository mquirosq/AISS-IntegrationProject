package aiss.vimeoMiner.service;

import aiss.vimeoMiner.vimeoModel.modelComment.Comments;
import aiss.vimeoMiner.vimeoModel.modelComment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getComments(String commentUri){
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

