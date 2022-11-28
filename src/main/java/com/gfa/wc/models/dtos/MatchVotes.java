package com.gfa.wc.models.dtos;

import com.gfa.wc.models.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchVotes {

  private Integer matchId;
  private Team homeTeam;
  private Team awayTeam;
  private long homeVoteCount;
  private long awayVoteCount;
  private long tieVoteCount;

}
