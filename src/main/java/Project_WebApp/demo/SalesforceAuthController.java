package Project_WebApp.demo;

import Project_WebApp.demo.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;

@Controller
public class SalesforceAuthController {

  private final SalesforceTokenService tokenService;

  @Value("${salesforce.client.id}")
  private String clientId;

  @Value("${salesforce.redirect.uri}")
  private String redirectUri;

  public SalesforceAuthController(SalesforceTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping("/salesforce/login")
  public void login(HttpServletResponse response, HttpSession session) throws IOException {

    String state = UUID.randomUUID().toString();
    session.setAttribute("oauth_state", state);

    String authUrl = "https://login.salesforce.com/services/oauth2/authorize"
        + "?response_type=code"
        + "&client_id=" + clientId
        + "&redirect_uri=" + redirectUri
        + "&state=" + state;

    response.sendRedirect(authUrl);
  }
}
