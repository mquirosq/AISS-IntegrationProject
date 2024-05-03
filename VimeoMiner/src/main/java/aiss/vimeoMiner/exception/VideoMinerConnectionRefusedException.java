package aiss.vimeoMiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.REQUEST_TIMEOUT, reason="Connection to database refused")
public class VideoMinerConnectionRefusedException extends Exception{

}
