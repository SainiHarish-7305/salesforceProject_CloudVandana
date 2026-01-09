package Project_WebApp.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

  @GetMapping("/dashboard")
  public String dashboard(HttpSession session, Model model) {

    // 🔒 Session protection
    if (session.getAttribute("ACCESS_TOKEN") == null) {
      return "redirect:/login";
    }

    // ✅ Send username to dashboard UI
    model.addAttribute("username", session.getAttribute("username"));

    return "Dashboard"; // Dashboard.html
  }
}
