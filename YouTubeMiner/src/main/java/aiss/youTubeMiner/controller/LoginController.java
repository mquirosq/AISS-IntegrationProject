package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.helper.ConstantsHelper;
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
            authenticator.tokenRequest(code);
            out = "<title>YouTubeMiner</title>" +
                    "<body>" +
                        "<h1>YouTubeMiner</h1>" +
                        "<p>Login completed, you may now close this page.</p>" +
                        "<p>If you wish to log out, <a href=\"" + ConstantsHelper.ipBase + "/logout\">click here</a>.</p>" +
                    "</body>";
        } else if (error != null) {
            out = "<title>YouTubeMiner</title>" +
                    "<body>" +
                        "<h1>YouTubeMiner</h1>" +
                        "<p>An error occurred during the login process:</p>" +
                        "<p>" + error + "</p>" +
                    "</body>";
        }
        return out;
    }

    @GetMapping("/done")
    public String donePage() {
        return "<body><p>DONE</p></body>";
    }

    @GetMapping("/login")
    public String loginPage() {
        return authenticator.authRequest();
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "<title>YouTubeMiner</title>" +
                "<body>" +
                    "<h1>YouTubeMiner</h1>" +
                    "<p>" + authenticator.revokeRequest() + "</p>" +
                "</body>";
    }
}
