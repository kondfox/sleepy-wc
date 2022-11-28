package com.gfa.wc.repositories;

import com.gfa.wc.models.entities.FootballMatch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<FootballMatch, Integer> {

  List<FootballMatch> findAll();

}
