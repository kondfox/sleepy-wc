package com.gfa.wc.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "matches")
public class FootballMatch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "home_team")
  private Team homeTeam;
  @ManyToOne
  @JoinColumn(name = "away_team")
  private Team awayTeam;
  @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
  private List<Vote> votes;

  public FootballMatch() {
    votes = new ArrayList<>();
  }

  public FootballMatch(Team homeTeam, Team awayTeam) {
    this();
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
  }

  public void addVote(Vote vote) {
    votes.add(vote);
  }

}
