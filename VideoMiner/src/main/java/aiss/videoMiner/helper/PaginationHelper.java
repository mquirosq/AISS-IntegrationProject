package aiss.videoMiner.helper;

import aiss.videoMiner.exception.OrderByPropertyDoesNotExistCaptionException;
import aiss.videoMiner.exception.OrderByPropertyDoesNotExistVideoException;
import aiss.videoMiner.model.Caption;
import aiss.videoMiner.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;

public class PaginationHelper {

    public static Page<Video> getVideoPage(int offset, int limit, List<Video> videos, String orderBy) throws OrderByPropertyDoesNotExistVideoException {

        Pageable pageRequest = PageRequest.of(offset, limit);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), videos.size());

        if (orderBy != null){
            Comparator<Video> comparator = getComparatorVideo(orderBy);
            videos.sort(comparator);
        }

        List<Video> pageContent = videos.subList(start, end);
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


    public static Page<Caption> getCaptionPage(int offset, int limit, List<Caption> captions, String orderBy) throws OrderByPropertyDoesNotExistCaptionException {

        Pageable pageRequest = PageRequest.of(offset, limit);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), captions.size());

        if (orderBy != null){
            Comparator<Caption> comparator = getComparatorCaption(orderBy);
            captions.sort(comparator);
        }

        List<Caption> pageContent = captions.subList(start, end);
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
}
