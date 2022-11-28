package com.gfa.wc.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "gamblers")
public class Gambler {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true)
  private String username;
  private String name;
  @JsonIgnore
  @Column(length = 512)
  private String password;
  @OneToMany(mappedBy = "gambler", cascade = CascadeType.ALL)
  private List<Vote> votes;
  private Long score;

  public Gambler() {
    votes = new ArrayList<>();
    score = 0L;
  }

  public Gambler(String name) {
    this();
    this.name = name;
  }

}
