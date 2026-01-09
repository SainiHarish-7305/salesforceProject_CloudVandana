package Project_WebApp.demo;

import Project_WebApp.demo.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SalesforceTokenService {
  @Value("${salesforce.client.id}")
  private String clientId;

  @Value("${salesforce.client.secret}")
  private String clientSecret;

  @Value("${salesforce.redirect.uri}")
  private String redirectUri;

  public TokenResponse exchangeCodeForToken(String code) {

    String tokenUrl = "https://login.salesforce.com/services/oauth2/token";

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String body = "grant_type=authorization_code"
        + "&code=" + code
        + "&client_id=" + clientId
        + "&client_secret=" + clientSecret
        + "&redirect_uri=" + redirectUri;

    HttpEntity<String> request = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

    Map<String, Object> responseBody = response.getBody();

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Failed to get access token");
    }
    if (response == null || response.getBody() == null) {
      throw new RuntimeException("Failed to get access token from Salesforce");
    }

    return new TokenResponse(
        (String) responseBody.get("access_token"),
        (String) responseBody.get("instance_url"));
  }
}
