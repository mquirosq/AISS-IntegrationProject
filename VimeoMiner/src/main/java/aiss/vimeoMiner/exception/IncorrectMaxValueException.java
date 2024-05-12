package aiss.vimeoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Max values should be 0 or greater")
public class IncorrectMaxValueException extends Exception{
}
