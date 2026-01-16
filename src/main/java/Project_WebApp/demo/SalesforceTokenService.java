package Project_WebApp.demo;

import Project_WebApp.demo.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.Map;

@Service
public class SalesforceTokenService {

  @Value("${salesforce.client.id}")
  private String clientId;

  @Value("${salesforce.client.secret}")
  private String clientSecret;

  @Value("${salesforce.redirect.uri}")
  private String redirectUri;

  private final RestTemplate restTemplate = new RestTemplate();

  public TokenResponse exchangeCodeForToken(String code) {

    String tokenUrl = "https://login.salesforce.com/services/oauth2/token";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String body = "grant_type=authorization_code"
        + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
        + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
        + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8)
        + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

    HttpEntity<String> request = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response;
    try {
      response = restTemplate.postForEntity(tokenUrl, request, Map.class);
    } catch (Exception e) {
      throw new RuntimeException("Salesforce token call failed", e);
    }

    if (response == null || response.getBody() == null) {
      throw new RuntimeException("Empty response from Salesforce token API");
    }

    Map<String, Object> responseBody = response.getBody();

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Salesforce error: " + responseBody);
    }

    return new TokenResponse(
        (String) responseBody.get("access_token"),
        (String) responseBody.get("instance_url"));
  }
}
