package com.gfa.wc.services;

import com.gfa.wc.models.entities.Team;
import com.gfa.wc.repositories.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  private TeamRepository teamRepository;

  public TeamService(TeamRepository teamRepository) {
    this.teamRepository = teamRepository;
  }

  public Iterable<Team> findAll() {
    return teamRepository.findAllByOrderByNameAsc();
  }

}
