package Project_WebApp.demo.controller;

import Project_WebApp.demo.SalesforceMetadataService;
import Project_WebApp.demo.dto.PendingRule;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

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
  public ResponseEntity<String> toggleValidationRule(
      @RequestParam String objectName,
      @RequestParam String ruleName,
      @RequestParam boolean enabled,
      HttpSession session) {

    List<PendingRule> pendingRules = (List<PendingRule>) session.getAttribute("PENDING_RULES");

    if (pendingRules == null) {
      pendingRules = new ArrayList<>();
    }

    // remove old entry if exists
    pendingRules.removeIf(r -> r.getObjectName().equals(objectName) &&
        r.getRuleName().equals(ruleName));

    PendingRule rule = new PendingRule();
    rule.setObjectName(objectName);
    rule.setRuleName(ruleName);
    rule.setActive(enabled);

    pendingRules.add(rule);

    session.setAttribute("PENDING_RULES", pendingRules);

    return ResponseEntity.ok("Change saved (not deployed)");
  }

  @PostMapping("/deploy")
  public ResponseEntity<String> deployValidationRules(HttpSession session) {

    String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
    String instanceUrl = (String) session.getAttribute("INSTANCE_URL");

    if (accessToken == null || instanceUrl == null) {
      return ResponseEntity.status(401).build();
    }

    List<PendingRule> pendingRules = (List<PendingRule>) session.getAttribute("PENDING_RULES");

    if (pendingRules == null || pendingRules.isEmpty()) {
      return ResponseEntity.badRequest().body("No changes to deploy");
    }

    for (PendingRule rule : pendingRules) {
      metadataService.toggleValidationRule(
          instanceUrl,
          accessToken,
          rule.getObjectName(),
          rule.getRuleName(),
          rule.isActive());
    }

    session.removeAttribute("PENDING_RULES");

    return ResponseEntity.ok("Validation rule deployed successfully");
  }

}
