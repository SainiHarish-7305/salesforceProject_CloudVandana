package Project_WebApp.demo.controller;

import Project_WebApp.demo.SalesforceMetadataService;
import Project_WebApp.demo.SalesforceService;
import Project_WebApp.demo.dto.ValidationRuleToggleRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation-rule")
public class ValidationRuleApiController {

  private final SalesforceMetadataService metadataService;

  public ValidationRuleApiController(SalesforceMetadataService metadataService) {
    this.metadataService = metadataService;
  }

  @GetMapping("/metadata")
  public ResponseEntity<?> fetchMetadata(HttpSession session) {

    String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
    String instanceUrl = (String) session.getAttribute("INSTANCE_URL");

    if (accessToken == null || instanceUrl == null) {
      return ResponseEntity.status(401).build();
    }

    return ResponseEntity.ok(
        metadataService.fetchMetadata(accessToken, instanceUrl));
  }

  @PostMapping("/toggle")
  public ResponseEntity<Void> toggleValidationRule(
      @RequestParam String objectName,
      @RequestParam String ruleName,
      @RequestParam boolean enabled,
      HttpSession session) {

    String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
    String instanceUrl = (String) session.getAttribute("INSTANCE_URL");

    if (accessToken == null || instanceUrl == null) {
      return ResponseEntity.status(401).build();
    }

    metadataService.toggleValidationRule(
        instanceUrl,
        accessToken,
        objectName,
        ruleName,
        enabled);

    return ResponseEntity.ok().build();
  }
}
