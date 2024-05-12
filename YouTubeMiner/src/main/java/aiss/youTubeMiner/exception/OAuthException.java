package aiss.youTubeMiner.exception;

import aiss.youTubeMiner.helper.ConstantsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.UNAUTHORIZED,
        reason = "OAuth authentication is required to perform operation. Please authenticate at " + ConstantsHelper.ipBase + "/login"
)
public class OAuthException extends Exception {

}
