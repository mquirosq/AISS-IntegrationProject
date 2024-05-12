package aiss.videoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason = "The property given as orderBy parameter cannot be found in the Video")
public class OrderByPropertyDoesNotExistVideoException extends Exception{

}
