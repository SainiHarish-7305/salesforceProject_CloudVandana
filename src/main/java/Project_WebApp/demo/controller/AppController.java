package Project_WebApp.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

  @GetMapping("/")
  public String home() {
    return "redirect:/login";
  }
}
