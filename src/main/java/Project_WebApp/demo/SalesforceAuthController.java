package Project_WebApp.demo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
public class SalesforceAuthController {

  private final SalesforceTokenService tokenService;

  @Value("${salesforce.client.id}")
  private String clientId;

  @Value("${salesforce.redirect.uri}")
  private String redirectUri;

  @Value("${salesforce.auth.url}")
  private String authUrl;

  public SalesforceAuthController(SalesforceTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping("/salesforce/login")
  public void login(HttpServletResponse response, HttpSession session) throws IOException {

    // ✅ Do NOT invalidate here
    // Just overwrite state to force new OAuth flow
    String state = UUID.randomUUID().toString();
    session.setAttribute("oauth_state", state);

    String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

    String redirect = authUrl
        + "?response_type=code"
        + "&client_id=" + clientId
        + "&redirect_uri=" + encodedRedirectUri
        + "&scope=full refresh_token"
        + "&state=" + state;

    response.sendRedirect(redirect);
  }
}
