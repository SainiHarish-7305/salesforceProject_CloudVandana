package Project_WebApp.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String loginPage() {
    return "Login";
  }

  @PostMapping("/login")
  public String doLogin(
      @RequestParam("username") String username,
      HttpSession session) {

    // ✅ STORE USERNAME IN SESSION
    session.setAttribute("username", username);

    // continue Salesforce OAuth
    return "redirect:/salesforce/login";
  }
}
