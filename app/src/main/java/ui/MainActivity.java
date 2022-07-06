package ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import aula.dio.simulator.aula.dio.R;
import aula.dio.simulator.aula.dio.data.MatchesAPI;
import aula.dio.simulator.aula.dio.databinding.ActivityMainBinding;
import domain.Matches;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ui.adapter.MatchesAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesAPI matchesApi;
    private MatchesAdapter matchesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();

    }

    private void setupHttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://denistomas.github.io/matches-simulador-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matchesApi = retrofit.create(MatchesAPI.class);
    }

    private void setupMatchesList() {
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));
        findMatchesApi();
    }



    private void setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener(this::findMatchesApi);
    }

    private void setupFloatingActionButton() {
      binding.fabSimulate.setOnClickListener(view -> {
          view.animate().rotationBy(360).setDuration(1000).setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                  Random random = new Random();
                  for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                      Matches matches = matchesAdapter.getMatches().get(i);
                      matches.getHomeTeam().setScore(random.nextInt(matches.getHomeTeam().getStars() +1));
                      matches.getAwayTeam().setScore(random.nextInt(matches.getAwayTeam().getStars() +1));
                      matchesAdapter.notifyItemChanged(i);
                  }
              }
          });
      });
    }

    private void showErrorMessage() {
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }

    private void findMatchesApi() {
        binding.srlMatches.setRefreshing(true);
        matchesApi.getMatches().enqueue(new Callback<List<Matches>>() {
            @Override
            public void onResponse(Call<List<Matches>> call, Response<List<Matches>> response) {
                if (response.isSuccessful()) {
                    List<Matches> matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvMatches.setAdapter(matchesAdapter);
                } else {
                    showErrorMessage();
                }
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Matches>> call, Throwable t) {
                showErrorMessage();
                binding.srlMatches.setRefreshing(false);
            }

        });
    }
}
