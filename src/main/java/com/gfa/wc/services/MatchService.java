package com.gfa.wc.services;

import com.gfa.wc.models.dtos.GamblerVote;
import com.gfa.wc.models.dtos.GamblerVotes;
import com.gfa.wc.models.dtos.MatchVotes;
import com.gfa.wc.models.entities.FootballMatch;

import java.util.NoSuchElementException;

public interface MatchService {

  Iterable<MatchVotes> findAll();

  MatchVotes findById(Integer id) throws NoSuchElementException;

  GamblerVotes findGamblerVotes(String username) throws NoSuchElementException;

  FootballMatch saveMatch(FootballMatch match);

  void vote(Integer matchId, GamblerVote gamblerVote) throws NoSuchElementException;

}
