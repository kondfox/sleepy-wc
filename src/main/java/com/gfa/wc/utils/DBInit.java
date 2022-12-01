package com.gfa.wc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.wc.models.dtos.ramin.LoginRequest;
import com.gfa.wc.models.dtos.ramin.LoginResponse;
import com.gfa.wc.models.dtos.ramin.MatchesResponse;
import com.gfa.wc.models.dtos.ramin.RaminError;
import com.gfa.wc.models.dtos.ramin.RaminMatch;
import com.gfa.wc.models.dtos.ramin.RaminTeam;
import com.gfa.wc.models.dtos.ramin.TeamResponse;
import com.gfa.wc.models.entities.FootballMatch;
import com.gfa.wc.models.entities.Team;
import com.gfa.wc.repositories.MatchRepository;
import com.gfa.wc.repositories.TeamRepository;
import com.gfa.wc.services.RaminService;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DBInit {

  private static final String baseURL = "http://api.cup2022.ir/api/v1";
  private TeamRepository teamRepository;
  private MatchRepository matchRepository;
  private LoginRequest loginRequest;
  private RaminService raminService;

  public DBInit(TeamRepository teamRepository, MatchRepository matchRepository, LoginRequest loginRequest) {
    this.teamRepository = teamRepository;
    this.matchRepository = matchRepository;
    this.loginRequest = loginRequest;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.cup2022.ir/api/v1/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    this.raminService = retrofit.create(RaminService.class);
  }

  public void initMatches() {
    LoginResponse loginResponse = login();
    String token = loginResponse.getData().getToken();
    MatchesResponse matchesResponse = getMatches(token);
    Set<Integer> teamIds = matchesResponse.getData().stream()
            .map(match -> Arrays.asList(match.getHomeTeamId(), match.getAwayTeamId()))
            .flatMap(ids -> ids.stream())
            .collect(Collectors.toSet());
    Map<Integer, String> teamIdCodes = teamIds.stream()
      .map(id -> getTeam(id, token))
      .collect(Collectors.toMap(RaminTeam::getId, match -> fifaCode(match.getFifaCode())));
    List<FootballMatch> matches = matchesResponse.getData().stream()
            .map(match -> convert(match, teamIdCodes))
            .collect(Collectors.toList());
    matchRepository.saveAll(matches);
  }

  private String fifaCode(String code) {
    switch (code) {
      case "NL":
        return "NED";
      case "SN":
        return "SEN";
      default:
        return code;
    }
  }

  private RaminTeam getTeam(Integer id, String token) {
    Call<TeamResponse> getTeamCall = raminService.getTeam(id, token);
    try {
      Response<TeamResponse> response = getTeamCall.execute();
      TeamResponse teams = response.body();
      return teams.getData().get(0);
    } catch (IOException e) {
      System.out.println("GET /team/{id} error");
      return null;
    }
  }

  private MatchesResponse getMatches(String token) {
    Call<MatchesResponse> getMatchesCall = raminService.getMatches(String.format("Bearer %s", token));
    try {
      Response<MatchesResponse> response = getMatchesCall.execute();
      return response.body();
    } catch (IOException e) {
      System.out.println("GET /match error");
      return null;
    }
  }

  private LoginResponse login() {
    Call<LoginResponse> loginCall = raminService.login(loginRequest);
    try {
      Response<?> response = loginCall.execute();
      if (response.body() instanceof LoginResponse) {
        return (LoginResponse) response.body();
      } else {
//        ResponseBody error = response.errorBody();
//        String errorString = error.string();
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

  public FootballMatch convert(RaminMatch raminMatch, Map<Integer, String> teamIdCodes) {
    FootballMatch match = new FootballMatch();
    String homeTeamCountryCode = teamIdCodes.get(raminMatch.getHomeTeamId());
    String awayTeamCountryCode = teamIdCodes.get(raminMatch.getAwayTeamId());
    Team homeTeam = new Team(homeTeamCountryCode);
    Team awayTeam = new Team(awayTeamCountryCode);
    match.setHomeTeam(homeTeam);
    match.setAwayTeam(awayTeam);
    return match;
  }

  public void initTeams() {
    List<Team> teams = Arrays.asList(
            new Team("SEN", "Senegal"),
            new Team("ECU", "Ecuador"),
            new Team("QAT", "Qatar"),
            new Team("NED", "Netherlands"),

            new Team("ENG", "England"),
            new Team("IRN", "Iran"),
            new Team("USA", "United States"),
            new Team("WAL", "Wales"),

            new Team("ARG", "Argentina"),
            new Team("MEX", "Mexico"),
            new Team("POL", "Poland"),
            new Team("KSA", "Saudi Arabia"),

            new Team("AUS", "Australia"),
            new Team("DEN", "Denmark"),
            new Team("FRA", "France"),
            new Team("TUN", "Tunisia"),

            new Team("CRC", "Costa Rica"),
            new Team("GER", "Germany"),
            new Team("JPN", "Japan"),
            new Team("ESP", "Spain"),

            new Team("BEL", "Belgium"),
            new Team("CAN", "Canada"),
            new Team("CRO", "Croatia"),
            new Team("MAR", "Morocco"),

            new Team("BRA", "Brazil"),
            new Team("CAM", "Cameroon"),
            new Team("SRB", "Serbia"),
            new Team("SUI", "Switzerland"),

            new Team("KOR", "South Korea"),
            new Team("POR", "Portugal"),
            new Team("URU", "Uruguay"),
            new Team("GHA", "Ghana")
    );
    teamRepository.saveAll(teams);
  }

}
