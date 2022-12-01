package com.gfa.wc.controllers.rest;

import com.gfa.wc.models.entities.Team;
import com.gfa.wc.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/teams")
public class TeamRestController {

  private TeamService teamService;

  public TeamRestController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping
  public ResponseEntity<Iterable<Team>> showAdminPage() {
    return ResponseEntity.status(200).body(teamService.findAll());
  }

}
