package aula.dio.simulator.aula.dio.data;

import java.util.List;

import domain.Matches;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MatchesAPI {

    @GET("matches.json")
    Call<List<Matches>> getMatches();
}