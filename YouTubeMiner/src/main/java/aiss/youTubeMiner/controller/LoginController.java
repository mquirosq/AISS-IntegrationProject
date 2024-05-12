package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.oauth2.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    Authenticator authenticator;

    @GetMapping("/")
    public String mainPage(@RequestParam(required = false) String code, @RequestParam(required = false) String error) {
        String out = "<title>YouTubeMiner</title>" +
                    "<body>" +
                        "<h1>YouTubeMiner</h1>" +
                        "<p>A YouTube datamining service</p>" +
                    "</body>";

        if (code != null) {
            authenticator.setToken(code);
            out = "<title>YouTubeMiner</title>" +
                    "<body>" +
                        "<h1>YouTubeMiner</h1>" +
                        "<p>Login completed, you may now close this page.</p>" +
                    "</body>";
        } else if (error != null) {
            authenticator.setToken(null);
            out = "<title>YouTubeMiner</title>" +
                    "<body>" +
                        "<h1>YouTubeMiner</h1>" +
                        "<p>An error occurred during the login process:</p>" +
                        "<p>" + error + "</p>" +
                    "</body>";
        }
        return out;
    }

    @GetMapping("/login")
    public String loginPage() {
        return authenticator.authRequest();
    }
}
