package aiss.vimeoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS, reason = "You tried to retrieve too many videos or comments")
public class TooManyRequestsException extends Exception{
}
