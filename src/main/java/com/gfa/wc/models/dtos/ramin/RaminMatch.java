package com.gfa.wc.models.dtos.ramin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaminMatch {

  private String group;
  @JsonProperty(value = "home_team_id")
  private Integer homeTeamId;
  @JsonProperty(value = "away_team_id")
  private Integer awayTeamId;
  private Boolean finished;

  public void setFinished(String finished) {
    this.finished = Boolean.parseBoolean(finished);
  }

}
