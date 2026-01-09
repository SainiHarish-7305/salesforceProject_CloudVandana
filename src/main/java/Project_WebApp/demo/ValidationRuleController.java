package Project_WebApp.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Project_WebApp.demo.controller.ToggleRequest;

@RestController
@RequestMapping("/api/validation-rule")
public class ValidationRuleController {

  private final SalesforceService salesforceService;

  public ValidationRuleController(SalesforceService salesforceService) {
    this.salesforceService = salesforceService;
  }

  @PostMapping("/toggle")
  public ResponseEntity<Void> toggle(
      @RequestBody ToggleRequest request,
      HttpSession session) {

    String accessToken = (String) session.getAttribute("accessToken");
    String instanceUrl = (String) session.getAttribute("instanceUrl");

    // Safety check
    if (accessToken == null || instanceUrl == null) {
      return ResponseEntity.status(401).build();
    }

    salesforceService.toggleValidationRule(
        request.getId(),
        request.isActive(),
        accessToken,
        instanceUrl);

    return ResponseEntity.ok().build();
  }
}
