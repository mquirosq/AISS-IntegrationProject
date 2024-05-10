package aiss.vimeoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS, reason = "The requests makes too many calls to the vimeo API. Try limiting the comments or videos fetched")
public class TooManyRequestsException extends Exception{
}
