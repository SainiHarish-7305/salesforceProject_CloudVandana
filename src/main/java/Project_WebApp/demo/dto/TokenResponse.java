package Project_WebApp.demo.dto;

public class TokenResponse {

  private String accessToken;
  private String instanceUrl;
  private String id; // Salesforce identity URL

  public TokenResponse(String accessToken, String instanceUrl, String id) {
    this.accessToken = accessToken;
    this.instanceUrl = instanceUrl;
    this.id = id;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getInstanceUrl() {
    return instanceUrl;
  }

  public String getId() {
    return id;
  }
}
