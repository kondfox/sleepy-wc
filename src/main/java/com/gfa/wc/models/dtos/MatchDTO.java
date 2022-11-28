package com.gfa.wc.models.dtos;

import com.gfa.wc.models.entities.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

  private Integer id;
  private Team homeTeam;
  private Team awayTeam;
  private MatchVotes votes;

}
