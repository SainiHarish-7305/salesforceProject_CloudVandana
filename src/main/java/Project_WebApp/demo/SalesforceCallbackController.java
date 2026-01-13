package Project_WebApp.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class SalesforceCallbackController {

  // @GetMapping("/salesforce/callback")
  // public String callback(
  // @RequestParam("code") String code,
  // HttpSession session) {

  // RestTemplate restTemplate = new RestTemplate();

  // String tokenUrl = "https://login.salesforce.com/services/oauth2/token"
  // + "?grant_type=authorization_code"
  // + "&client_id=YOUR_CLIENT_ID"
  // + "&client_secret=YOUR_CLIENT_SECRET"
  // + "&redirect_uri=http://localhost:8080/salesforce/callback"
  // + "&code=" + code;

  // @SuppressWarnings("unchecked")
  // Map<String, Object> response = restTemplate.postForObject(tokenUrl, null,
  // Map.class);

  // String accessToken = (String) response.get("access_token");
  // String instanceUrl = (String) response.get("instance_url");

  // // 🔥 STORE IN SESSION (THIS WAS MISSING)
  // session.setAttribute("accessToken", accessToken);
  // session.setAttribute("instanceUrl", instanceUrl);

  // return "redirect:/dashboard";

  // @Controller
  // public class SalesforceCallbackController {

  @GetMapping("/oauth/callback")
  public String callback(
      @RequestParam String access_token,
      @RequestParam String instance_url,
      HttpSession session) {
    session.setAttribute("accessToken", access_token);
    session.setAttribute("instanceUrl", instance_url);

    return "redirect:/dashboard";
  }
}
