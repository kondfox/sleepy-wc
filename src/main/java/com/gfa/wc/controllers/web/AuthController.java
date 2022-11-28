package com.gfa.wc.controllers.web;

import com.gfa.wc.models.entities.Gambler;
import com.gfa.wc.services.GamblerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  private GamblerService gamblerService;

  public AuthController(GamblerService gamblerService) {
    this.gamblerService = gamblerService;
  }

  @GetMapping("/registration")
  public String showRegistrationPage() {
    return "registration";
  }

  @PostMapping("/registration")
  public String register(@ModelAttribute Gambler user, Model model) {
    try {
      gamblerService.save(user);
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", e.getMessage());
      return "registration";
    }
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String showLoginPage() {
    return "login";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute Gambler user, Model model) {
    try {
      gamblerService.login(user);
      return String.format("redirect:/users/%s", user.getUsername());
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "login";
    }
  }

}
