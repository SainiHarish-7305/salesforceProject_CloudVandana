package Project_WebApp.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SalesforceMetadataService {

  public void toggleValidationRule(
      String instanceUrl,
      String accessToken,
      String objectName,
      String ruleName,
      boolean active) {

    String soapEndpoint = instanceUrl + "/services/Soap/m/58.0";

    String soapBody = """
        <env:Envelope xmlns:env="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:met="http://soap.sforce.com/2006/04/metadata">
          <env:Header>
            <met:SessionHeader>
              <met:sessionId>%s</met:sessionId>
            </met:SessionHeader>
          </env:Header>
          <env:Body>
            <met:updateMetadata>
              <met:metadata xsi:type="met:ValidationRule"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <met:fullName>%s.%s</met:fullName>
                <met:active>%s</met:active>
              </met:metadata>
            </met:updateMetadata>
          </env:Body>
        </env:Envelope>
        """.formatted(accessToken, objectName, ruleName, active);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_XML);
    headers.add("SOAPAction", "updateMetadata");

    HttpEntity<String> request = new HttpEntity<>(soapBody, headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response = restTemplate.postForEntity(
        soapEndpoint,
        request,
        String.class);

    System.out.println("SOAP STATUS = " + response.getStatusCode());
    System.out.println("SOAP RESPONSE = ");
    System.out.println(response.getBody());

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Metadata update failed: " + response.getBody());
    }

  }

  public Object fetchMetadata(String accessToken, String instanceUrl) {

    throw new UnsupportedOperationException("Unimplemented method 'fetchMetadata'");
  }
}
