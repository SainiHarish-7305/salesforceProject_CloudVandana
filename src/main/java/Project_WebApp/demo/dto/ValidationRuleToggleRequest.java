package Project_WebApp.demo.dto;

public class ValidationRuleToggleRequest {

  private String ruleId;
  private boolean active;

  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
