package Project_WebApp.demo.controller;

import Project_WebApp.demo.SalesforceService;
import Project_WebApp.demo.dto.ValidationRuleToggleRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation-rule")
public class ValidationRuleApiController {

  private final SalesforceService salesforceService;

  public ValidationRuleApiController(SalesforceService salesforceService) {
    this.salesforceService = salesforceService;
  }

  @GetMapping("/metadata")
  public ResponseEntity<?> fetchMetadata(HttpSession session) {

    String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
    String instanceUrl = (String) session.getAttribute("INSTANCE_URL");

    if (accessToken == null || instanceUrl == null) {
      return ResponseEntity.status(401).build();
    }

    return ResponseEntity.ok(
        salesforceService.fetchMetadata(accessToken, instanceUrl));
  }

  @PostMapping("/toggle")
  public ResponseEntity<Void> toggleValidationRule(
      @RequestBody ValidationRuleToggleRequest request,
      HttpSession session) {

    String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
    String instanceUrl = (String) session.getAttribute("INSTANCE_URL");

    if (accessToken == null || instanceUrl == null) {
      return ResponseEntity.status(401).build();
    }

    salesforceService.toggleValidationRule(
        request.getRuleId(),
        request.isActive(),
        accessToken,
        instanceUrl);

    return ResponseEntity.ok().build();
  }
}
