package Project_WebApp.demo;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Project_WebApp.demo.dto.TokenResponse;

@Controller
public class SalesforceCallbackController {

  private final SalesforceTokenService tokenService;

  public SalesforceCallbackController(SalesforceTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping("/oauth/callback")
  public String callback(
      @RequestParam("code") String code,
      @RequestParam("state") String state,
      HttpSession session) {

    String savedState = (String) session.getAttribute("oauth_state");
    if (savedState == null || !savedState.equals(state)) {
      return "redirect:/error?msg=Invalid OAuth state";
    }

    session.removeAttribute("oauth_state");

    // 🔹 Exchange code for token (ONLY ONCE)
    TokenResponse tokenResponse = tokenService.exchangeCodeForToken(code);

    // 🔹 Fetch Salesforce user info
    Map<String, Object> userInfo = tokenService.fetchUserInfo(
        tokenResponse.getAccessToken(),
        tokenResponse.getId());

    // 🔹 Store user details
    session.setAttribute("SF_USERNAME", userInfo.get("username"));
    session.setAttribute("SF_EMAIL", userInfo.get("email"));
    session.setAttribute("SF_NAME", userInfo.get("display_name"));

    // 🔹 Store token info
    session.setAttribute("ACCESS_TOKEN", tokenResponse.getAccessToken());
    session.setAttribute("INSTANCE_URL", tokenResponse.getInstanceUrl());

    return "redirect:/dashboard";
  }
}