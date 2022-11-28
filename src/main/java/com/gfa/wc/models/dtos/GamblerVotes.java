package com.gfa.wc.models.dtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GamblerVotes {

  private String username;
  private Map<Integer, String> votes;

}
