package com.gfa.wc.controllers.rest;

import com.gfa.wc.models.dtos.ErrorMessage;
import com.gfa.wc.models.dtos.GamblerVote;
import com.gfa.wc.models.dtos.MatchVotes;
import com.gfa.wc.models.entities.FootballMatch;
import com.gfa.wc.services.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/matches")
public class MatchRestController {

  private MatchService matchService;

  public MatchRestController(MatchService matchService) {
    this.matchService = matchService;
  }

  @GetMapping
  public ResponseEntity<Iterable<MatchVotes>> findAllMatches() {
    return ResponseEntity.status(200).body(matchService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findMatch(@PathVariable Integer id) {
    try {
      return ResponseEntity.ok(matchService.findById(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(404).body(new ErrorMessage("No such match id"));
    }
  }

  @PostMapping
  public ResponseEntity<FootballMatch> createNewMatch(@RequestBody FootballMatch match) {
    return ResponseEntity.status(HttpStatus.CREATED).body(matchService.saveMatch(match));
  }

  @PostMapping("/{matchId}/votes")
  public ResponseEntity<?> vote(@PathVariable Integer matchId, @RequestBody GamblerVote gamblerVote) {
    matchService.vote(matchId, gamblerVote);
    return ResponseEntity.status(204).build();
  }

}
