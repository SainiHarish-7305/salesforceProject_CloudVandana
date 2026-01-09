package Project_WebApp.demo.dto;

public class TokenResponse {

  private final String accessToken;
  private final String instanceUrl;

  public TokenResponse(String accessToken, String instanceUrl) {
    this.accessToken = accessToken;
    this.instanceUrl = instanceUrl;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getInstanceUrl() {
    return instanceUrl;
  }
}
