package com.gfa.wc.controllers.web;

import com.gfa.wc.models.entities.FootballMatch;
import com.gfa.wc.services.MatchServiceImpl;
import com.gfa.wc.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private TeamService teamService;
  private MatchServiceImpl matchService;

  public AdminController(TeamService teamService, MatchServiceImpl matchService) {
    this.teamService = teamService;
    this.matchService = matchService;
  }

  @GetMapping
  public String showAdminPage(Model model) {
    model.addAttribute("teams", teamService.findAll());
    return "admin";
  }

  @PostMapping("/matches")
  public String createNewMatch(@ModelAttribute FootballMatch match) {
    matchService.saveMatch(match);
    return "redirect:/admin";
  }

}
