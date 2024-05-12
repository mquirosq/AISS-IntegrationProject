package aiss.videoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Limit should be over 0 and offset should not be negative")
public class InvalidPageParametersException extends Exception{

}
