package aiss.youTubeMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Comments not found")
public class VideoCommentsNotFoundException extends Exception {
}
