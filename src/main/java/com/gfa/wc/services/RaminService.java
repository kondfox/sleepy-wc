package com.gfa.wc.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.wc.models.dtos.ramin.LoginRequest;
import com.gfa.wc.models.dtos.ramin.LoginResponse;
import com.gfa.wc.models.dtos.ramin.MatchesResponse;
import com.gfa.wc.models.dtos.ramin.RaminError;
import com.gfa.wc.models.dtos.ramin.RaminTeam;
import com.gfa.wc.models.dtos.ramin.TeamResponse;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Service
public class RaminService {

  private RaminRetrofitService service;
  private LoginRequest loginRequest;

  public RaminService(LoginRequest loginRequest) {
    this.loginRequest = loginRequest;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.cup2022.ir/api/v1/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    this.service = retrofit.create(RaminRetrofitService.class);
  }

  public LoginResponse login() {
    Call<LoginResponse> loginCall = service.login(loginRequest);
    try {
      Response<?> response = loginCall.execute();
      if (response.body() instanceof LoginResponse) {
        return (LoginResponse) response.body();
      } else {
        ObjectMapper objectMapper = new ObjectMapper();
        RaminError raminError = objectMapper.readValue(response.errorBody().string(), RaminError.class);
        System.out.println(raminError);
        return null;
      }
    } catch (IOException e) {
      System.out.println("Login error");
      return null;
    }
  }

  public RaminTeam getTeam(Integer id, String token) {
    Call<TeamResponse> getTeamCall = service.getTeam(id, token);
    try {
      Response<TeamResponse> response = getTeamCall.execute();
      TeamResponse teams = response.body();
      return teams.getData().get(0);
    } catch (IOException e) {
      System.out.println("GET /team/{id} error");
      return null;
    }
  }

  public MatchesResponse getMatches(String token) {
    Call<MatchesResponse> getMatchesCall = service.getMatches(String.format("Bearer %s", token));
    try {
      Response<MatchesResponse> response = getMatchesCall.execute();
      return response.body();
    } catch (IOException e) {
      System.out.println("GET /match error");
      return null;
    }
  }

}
