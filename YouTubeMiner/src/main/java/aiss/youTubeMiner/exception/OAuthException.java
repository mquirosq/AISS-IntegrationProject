package aiss.youTubeMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "OAuth authentication is required to perform operation.")
public class OAuthException extends Exception {

}
