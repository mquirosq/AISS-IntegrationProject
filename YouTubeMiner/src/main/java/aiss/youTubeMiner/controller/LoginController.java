package aiss.youTubeMiner.controller;

import aiss.youTubeMiner.helper.ConstantsHelper;
import aiss.youTubeMiner.oauth2.Authenticator;
import aiss.youTubeMiner.videoModel.VComment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login", description = "Responsible for authentication-related pages")
@RestController
public class LoginController {
    @Autowired
    Authenticator authenticator;

    @Operation(
            summary="Access main page",
            description = "Access main HTML page of YouTubeMiner. This page is also the redirect URI for OAuth",
            tags= {"login", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(), mediaType="text/html")})
    })
    @GetMapping("/")
    public String mainPage(@Parameter(hidden = true) @RequestParam(required = false) String code, @Parameter(hidden = true) @RequestParam(required = false) String error) {
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

    @Operation(
            summary="Access login page",
            description = "Access the login page. This redirects the user to the Google OAuth page.",
            tags= {"login", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(), mediaType="text/html")})
    })
    @GetMapping("/login")
    public String loginPage() {
        return authenticator.authRequest();
    }

    @Operation(
            summary="Access logout page",
            description = "Access the logout page. This revokes the users's access token.",
            tags= {"comments", "get", "logout"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema=
            @Schema(), mediaType="text/html")})
    })
    @GetMapping("/logout")
    public String logoutPage() {
        return "<title>YouTubeMiner</title>" +
                "<body>" +
                    "<h1>YouTubeMiner</h1>" +
                    "<p>" + authenticator.revokeRequest() + "</p>" +
                "</body>";
    }
}
