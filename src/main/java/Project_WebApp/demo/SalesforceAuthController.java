package Project_WebApp.demo;

import Project_WebApp.demo.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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
  public void login(
      @RequestParam(defaultValue = "prod") String env,
      HttpServletResponse response) throws IOException {

    String baseUrl = env.equals("sandbox")
        ? "https://test.salesforce.com"
        : "https://login.salesforce.com";

    String authUrl = baseUrl + "/services/oauth2/authorize"
        + "?response_type=code"
        + "&client_id=" + clientId
        + "&redirect_uri=" + redirectUri;

    response.sendRedirect(authUrl);
  }

  @GetMapping("/callback")
  public String callback(
      @RequestParam String code,
      HttpSession session) {

    TokenResponse tokenResponse = tokenService.exchangeCodeForToken(code);

    session.setAttribute("ACCESS_TOKEN", tokenResponse.getAccessToken());
    session.setAttribute("INSTANCE_URL", tokenResponse.getInstanceUrl());

    return "redirect:/dashboard";
  }
}
