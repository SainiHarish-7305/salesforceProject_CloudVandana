package Project_WebApp.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SalesforceService {

  /*
   * =========================
   * FETCH VALIDATION RULES
   * =========================
   */
  public Object fetchMetadata(String accessToken, String instanceUrl) {

    String url = instanceUrl
        + "/services/data/v59.0/tooling/query"
        + "?q=SELECT+Id,ValidationName,Active,EntityDefinition.QualifiedApiName"
        + "+FROM+ValidationRule";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

    return response.getBody();
  }


  public void toggleValidationRule(
      String ruleId,
      boolean active,
      String accessToken,
      String instanceUrl) {

    String url = instanceUrl
        + "/services/data/v59.0/tooling/sobjects/ValidationRule/"
        + ruleId;

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("X-HTTP-Method-Override", "PATCH");

    Map<String, Object> body = new HashMap<>();
    body.put("Active", active);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

    RestTemplate restTemplate = new RestTemplate();

    try {
      restTemplate.exchange(
          url,
          HttpMethod.POST,
          entity,
          Void.class);
    } catch (Exception e) {
      System.out.println("❌ ERROR WHILE UPDATING VALIDATION RULE");
      e.printStackTrace(); // ← THIS WILL SHOW REAL SALESFORCE ERROR
      throw e;
    }
  }

}
