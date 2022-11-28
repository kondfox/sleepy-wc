package com.gfa.wc.repositories;

import com.gfa.wc.models.entities.Gambler;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GamblerRepository extends CrudRepository<Gambler, Integer> {

  Optional<Gambler> findByUsername(String username);

}
