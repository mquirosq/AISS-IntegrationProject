package aiss.vimeoMiner.helper;

import org.springframework.http.HttpHeaders;

import static aiss.vimeoMiner.helper.ConstantsHelper.apiAuthorizationString;

public class AuthenticationHelper {

    public static HttpHeaders createHttpHeaderAuthentication(){
        return new HttpHeaders(){
            {
                set("Authorization", apiAuthorizationString);
            }
        };
    }
}
