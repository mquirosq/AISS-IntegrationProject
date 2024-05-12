package aiss.videoMiner.helper;

import aiss.videoMiner.exception.*;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Comment;
import aiss.videoMiner.model.User;
import aiss.videoMiner.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PaginationHelper {

    public static Page<Video> getVideoPage(int offset, int limit, List<Video> videos, String orderBy) throws OrderByPropertyDoesNotExistVideoException, InvalidPageParametersException {

        if (limit <= 0 || offset < 0){
            throw new InvalidPageParametersException();
        }

        Pageable pageRequest = PageRequest.of(offset, limit);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), videos.size());

        if (orderBy != null){
            Comparator<Video> comparator = getComparatorVideo(orderBy);
            videos.sort(comparator);
        }

        List<Video> pageContent = new ArrayList<>();
        if (start <= end){
            pageContent = videos.subList(start, end);
        }

        return new PageImpl<>(pageContent, pageRequest, videos.size());
    }

    private static Comparator<Video> getComparatorVideo(String orderBy) throws OrderByPropertyDoesNotExistVideoException {

        Comparator<Video> comparator = switch (orderBy.startsWith("-") ? orderBy.substring(1) : orderBy) {
            case "name" -> Comparator.comparing(Video::getName);
            case "description" -> Comparator.comparing(Video::getDescription);
            case "releaseTime" -> Comparator.comparing(Video::getReleaseTime);
            default -> throw new OrderByPropertyDoesNotExistVideoException();
        };

        if (orderBy.startsWith("-")){
            comparator = comparator.reversed();
        }
        return comparator;
    }


    public static Page<Caption> getCaptionPage(int offset, int limit, List<Caption> captions, String orderBy) throws OrderByPropertyDoesNotExistCaptionException, InvalidPageParametersException {

        if (limit <= 0 || offset < 0){
            throw new InvalidPageParametersException();
        }

        Pageable pageRequest = PageRequest.of(offset, limit);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), captions.size());

        if (orderBy != null){
            Comparator<Caption> comparator = getComparatorCaption(orderBy);
            captions.sort(comparator);
        }
        List<Caption> pageContent = new ArrayList<>();
        if (start <= end){
            pageContent = captions.subList(start, end);
        }
        return new PageImpl<>(pageContent, pageRequest, captions.size());
    }

    private static Comparator<Caption> getComparatorCaption(String orderBy) throws OrderByPropertyDoesNotExistCaptionException {

        Comparator<Caption> comparator = switch (orderBy.startsWith("-") ? orderBy.substring(1) : orderBy) {
            case "name" -> Comparator.comparing(Caption::getName);
            case "language" -> Comparator.comparing(Caption::getLanguage);
            default -> throw new OrderByPropertyDoesNotExistCaptionException();
        };

        if (orderBy.startsWith("-")){
            comparator = comparator.reversed();
        }
        return comparator;
    }

    public static Page<Comment> getCommentPage(int offset, int limit, List<Comment> comments, String orderBy) throws OrderByPropertyDoesNotExistCommentException, InvalidPageParametersException {

        if (limit <= 0 || offset < 0){
            throw new InvalidPageParametersException();
        }

        Pageable pageRequest = PageRequest.of(offset, limit);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), comments.size());

        if (orderBy != null){
            Comparator<Comment> comparator = getComparatorComment(orderBy);
            comments.sort(comparator);
        }

        List<Comment> pageContent = new ArrayList<>();
        if (start <= end){
            pageContent = comments.subList(start, end);
        }
        return new PageImpl<>(pageContent, pageRequest, comments.size());
    }

    private static Comparator<Comment> getComparatorComment(String orderBy) throws OrderByPropertyDoesNotExistCommentException {

        Comparator<Comment> comparator = switch (orderBy.startsWith("-") ? orderBy.substring(1) : orderBy) {
            case "text" -> Comparator.comparing(Comment::getText);
            case "createdOn" -> Comparator.comparing(Comment::getCreatedOn);
            case "author" -> Comparator.comparing(comment -> comment.getAuthor().getName());
            default -> throw new OrderByPropertyDoesNotExistCommentException();
        };

        if (orderBy.startsWith("-")){
            comparator = comparator.reversed();
        }
        return comparator;
    }
}
