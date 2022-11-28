package com.gfa.wc.repositories;

import com.gfa.wc.models.entities.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, String> {

  Iterable<Team> findAllByOrderByNameAsc();

}
