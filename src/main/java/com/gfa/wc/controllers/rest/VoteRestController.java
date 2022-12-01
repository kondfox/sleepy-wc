package com.gfa.wc.controllers.rest;

import com.gfa.wc.models.dtos.GamblerVotes;
import com.gfa.wc.models.entities.Vote;
import com.gfa.wc.services.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/votes")
public class VoteRestController {

  private MatchService matchService;

  public VoteRestController(MatchService matchService) {
    this.matchService = matchService;
  }

  @GetMapping("/{username}")
  public ResponseEntity<GamblerVotes> getUserVotes(@PathVariable String username) {
    GamblerVotes gamblerVotes = matchService.findGamblerVotes(username);
    return ResponseEntity.ok(gamblerVotes);
  }

}
