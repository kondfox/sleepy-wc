package com.gfa.wc.repositories;

import com.gfa.wc.models.entities.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, String> {

  @Query(value = "SELECT gamblers.name FROM votes JOIN gamblers ON gamblers.id = votes.gambler_id" +
          "JOIN matches ON matches.id = votes.match_id" +
          "WHERE matches.id = ?1" +
          "AND votes.vote = ?2" +
          "ORDER BY gamblers.name", nativeQuery = true)
  List<String> findUsersNamesWhoVoted(Integer matchId, String vote);

}
