package com.gfa.wc.models;

public enum VoteOption {

  HOME("HOME"),
  AWAY("AWAY"),
  TIE("TIE");

  private String option;

  VoteOption(String option) {
    this.option = option;
  }

  @Override
  public String toString() {
    return option;
  }

}
