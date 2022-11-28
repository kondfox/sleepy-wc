package com.gfa.wc.models.dtos.ramin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchesResponse {

  private String status;
  private List<RaminMatch> data;

  public MatchesResponse() {
    data = new ArrayList<>();
  }

}
