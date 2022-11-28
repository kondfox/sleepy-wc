package com.gfa.wc.models.dtos.ramin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RaminTeam {

  private Integer id;
  @JsonProperty(value = "fifa_code")
  private String fifaCode;

  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }

}
