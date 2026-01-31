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

  @Value("${salesforce.token.url}")
  private String tokenUrl;

  private final RestTemplate restTemplate = new RestTemplate();

  public TokenResponse exchangeCodeForToken(String code) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String body = "grant_type=authorization_code"
        + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
        + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
        + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8)
        + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

    HttpEntity<String> request = new HttpEntity<>(body, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

    if (response == null || response.getBody() == null) {
      throw new RuntimeException("Empty response from Salesforce token API");
    }

    Map<String, Object> responseBody = response.getBody();

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Salesforce error: " + responseBody);
    }

    return new TokenResponse(
        (String) responseBody.get("access_token"),
        (String) responseBody.get("instance_url"),
        (String) responseBody.get("id"));
  }

  public Map<String, Object> fetchUserInfo(String accessToken, String idUrl) {

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<Map> response = restTemplate.exchange(idUrl, HttpMethod.GET, entity, Map.class);

    return response.getBody();
  }
}
