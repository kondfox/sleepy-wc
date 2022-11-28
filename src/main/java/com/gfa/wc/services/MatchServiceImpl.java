package com.gfa.wc.services;

import com.gfa.wc.models.dtos.GamblerVote;
import com.gfa.wc.models.dtos.GamblerVotes;
import com.gfa.wc.models.dtos.MatchDTO;
import com.gfa.wc.models.dtos.MatchVotes;
import com.gfa.wc.models.entities.FootballMatch;
import com.gfa.wc.models.VoteOption;
import com.gfa.wc.models.entities.Gambler;
import com.gfa.wc.models.entities.Vote;
import com.gfa.wc.repositories.GamblerRepository;
import com.gfa.wc.repositories.MatchRepository;
import com.gfa.wc.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

  private MatchRepository matchRepository;
  private GamblerRepository gamblerRepository;
  private VoteRepository voteRepository;

  public MatchServiceImpl(MatchRepository matchRepository, GamblerRepository gamblerRepository, VoteRepository voteRepository) {
    this.matchRepository = matchRepository;
    this.gamblerRepository = gamblerRepository;
    this.voteRepository = voteRepository;
  }

  public Iterable<MatchVotes> findAll() {
    List<FootballMatch> matches = matchRepository.findAll();
    List<MatchVotes> matchVotes = matches.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    return matchVotes;
  }

  @Override
  public MatchVotes findById(Integer id) throws NoSuchElementException {
    FootballMatch match = matchRepository.findById(id).orElseThrow(NoSuchElementException::new);
    MatchVotes matchVotes = convert(match);
    return matchVotes;
  }

  public GamblerVotes findGamblerVotes(String username) throws NoSuchElementException {
    Gambler gambler = gamblerRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
    return convert(gambler);
  }

  private MatchVotes convert(FootballMatch match) {
    MatchVotes matchVotes = new MatchVotes();
    matchVotes.setMatchId(match.getId());
    matchVotes.setHomeTeam(match.getHomeTeam());
    matchVotes.setAwayTeam(match.getAwayTeam());
    matchVotes.setHomeVoteCount(countVotes(match, VoteOption.HOME));
    matchVotes.setAwayVoteCount(countVotes(match, VoteOption.AWAY));
    matchVotes.setTieVoteCount(countVotes(match, VoteOption.TIE));
    return matchVotes;
  }

  private GamblerVotes convert(Gambler gambler) {
    GamblerVotes gamblerVotes = new GamblerVotes();
    gamblerVotes.setUsername(gambler.getUsername());
    Map<Integer, String> matchVotes = gambler.getVotes().stream()
            .collect(Collectors.toMap(vote -> vote.getMatch().getId(), vote -> vote.getVote().toString()));
    gamblerVotes.setVotes(matchVotes);
    return gamblerVotes;
  }

  private long countVotes(FootballMatch match, VoteOption voteOption) {
    return match.getVotes().stream()
            .filter(vote -> vote.getVote().equals(voteOption))
            .count();
  }

  public FootballMatch saveMatch(FootballMatch match) {
    return matchRepository.save(match);
  }

  public void vote(Integer matchId, GamblerVote gamblerVote) throws NoSuchElementException {
    FootballMatch match = matchRepository.findById(matchId).orElseThrow(NoSuchElementException::new);
    Gambler gambler = gamblerRepository.findByUsername(gamblerVote.getUsername()).orElseThrow(NoSuchElementException::new);
    Vote vote = new Vote();
    vote.setMatch(match);
    vote.setVote(VoteOption.valueOf(gamblerVote.getVote()));
    vote.setGambler(gambler);
    voteRepository.save(vote);
  }

}
