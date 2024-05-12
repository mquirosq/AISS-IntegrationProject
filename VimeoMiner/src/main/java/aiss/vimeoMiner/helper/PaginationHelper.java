package aiss.vimeoMiner.helper;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class PaginationHelper {

    // Compute page and items per page given a number of items
    public static Pair<Integer, Integer> getPageAndItemsPerPage(Integer maxItems){
        Pair<Integer, Integer> pageAndItemsPerPage = null;
        if (maxItems<100){
            pageAndItemsPerPage= Pair.of(1, maxItems);
        }
        return pageAndItemsPerPage;
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
