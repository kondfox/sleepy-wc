package com.gfa.wc.services;

import com.gfa.wc.models.dtos.ramin.LoginRequest;
import com.gfa.wc.models.dtos.ramin.LoginResponse;
import com.gfa.wc.models.dtos.ramin.MatchesResponse;
import com.gfa.wc.models.dtos.ramin.TeamResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RaminService {

  @POST("user/login")
  Call<LoginResponse> login(@Body LoginRequest loginRequest);

  @GET("match")
  Call<MatchesResponse> getMatches(@Header("Authorization") String auth);

  @GET("team/{teamId}")
  Call<TeamResponse> getTeam(@Path("teamId") Integer teamId, @Header("Authorization") String auth);

}
