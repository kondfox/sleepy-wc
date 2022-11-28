package com.gfa.wc.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gfa.wc.models.VoteOption;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "votes")
public class Vote {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  @ManyToOne
  private FootballMatch match;
  @ManyToOne
  private Gambler gambler;
  @Enumerated(EnumType.STRING)
  private VoteOption vote;

}
