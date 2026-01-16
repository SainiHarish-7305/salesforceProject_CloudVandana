package Project_WebApp.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SalesforceApiService {

  private final RestTemplate restTemplate = new RestTemplate();

  public void updateAccountActive(
      String instanceUrl,
      String accessToken,
      String accountId,
      boolean active) {

    String url = instanceUrl
        + "/services/data/v58.0/sobjects/Account/"
        + accountId;

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> body = new HashMap<>();
    body.put("Active__c", active);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    restTemplate.patchForObject(url, request, String.class);
  }
}