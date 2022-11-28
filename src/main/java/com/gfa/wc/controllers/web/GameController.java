package com.gfa.wc.controllers.web;

import com.gfa.wc.models.dtos.GamblerVote;
import com.gfa.wc.services.MatchService;
import com.gfa.wc.services.MatchServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

@Controller
public class GameController {

  private MatchService matchService;

  public GameController(MatchService matchService) {
    this.matchService = matchService;
  }

  @GetMapping("/users/{username}")
  public String showMainPage(@PathVariable String username, Model model) {
    model.addAttribute("gamblerVotes", matchService.findGamblerVotes(username));
    model.addAttribute("matches", matchService.findAll());
    return "index";
  }

  @PostMapping("/matches/{matchId}/votes")
  public String vote(@PathVariable Integer matchId, @ModelAttribute GamblerVote vote, Model model) {
    try {
      matchService.vote(matchId, vote);
      return String.format("redirect:/users/%s", vote.getUsername());
    } catch (NoSuchElementException e) {
      model.addAttribute("error", String.format("No such match id: %d...", matchId));
      return "error";
    }
  }

}
