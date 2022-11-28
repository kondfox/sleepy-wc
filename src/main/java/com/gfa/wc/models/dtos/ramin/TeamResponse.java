package com.gfa.wc.models.dtos.ramin;

import lombok.Data;

import java.util.List;

@Data
public class TeamResponse {

  private String status;
  private List<RaminTeam> data;

}
