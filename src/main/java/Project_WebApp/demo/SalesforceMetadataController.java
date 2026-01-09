package Project_WebApp.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesforceMetadataController {

  private final SalesforceService salesforceService;

  public SalesforceMetadataController(SalesforceService salesforceService) {
    this.salesforceService = salesforceService;
  }

  @GetMapping("/api/metadata")
  public Object getMetadata(HttpSession session) {

    String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
    String instanceUrl = (String) session.getAttribute("INSTANCE_URL");

    // 🔒 session guard
    if (accessToken == null || instanceUrl == null) {
      throw new RuntimeException("Session expired. Please login again.");
    }

    return salesforceService.fetchMetadata(accessToken, instanceUrl);
  }
}
