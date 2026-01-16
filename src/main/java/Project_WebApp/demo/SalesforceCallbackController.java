package Project_WebApp.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import Project_WebApp.demo.dto.TokenResponse;

import java.util.Map;

@Controller
public class SalesforceCallbackController {

  private final SalesforceTokenService tokenService;

  public SalesforceCallbackController(SalesforceTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @GetMapping("/oauth/callback")
  public String callback(@RequestParam("code") String code,
      @RequestParam(value = "state", required = false) String state,
      HttpSession session) {

    String savedState = (String) session.getAttribute("oauth_state");
    if (savedState != null && !savedState.equals(state)) {
      return "redirect:/error?msg=Invalid OAuth state";
    }

    TokenResponse tokenResponse = tokenService.exchangeCodeForToken(code);

    session.setAttribute("ACCESS_TOKEN", tokenResponse.getAccessToken());
    session.setAttribute("INSTANCE_URL", tokenResponse.getInstanceUrl());

    return "redirect:/dashboard";
  }
}
