package ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import aula.dio.simulator.aula.dio.databinding.MatchItemBinding;
import domain.Matches;
import ui.DetailActivity;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private List<Matches> matches;

    public MatchesAdapter(List<Matches> matches) {
        this.matches = matches;
    }

    public List<Matches> getMatches() {
        return matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Matches matches = this.matches.get(position);

        //Adapts data from the match (recovered from API to our layout).
        Glide.with(context).load(matches.getHomeTeam().getImage()).circleCrop().into(holder.binding.idHomeTeam);
        holder.binding.tvHomeTeam.setText(matches.getHomeTeam().getName());
        if (matches.getHomeTeam().getScore() != null) {
            holder.binding.tvHomeScore.setText(String.valueOf(matches.getHomeTeam().getScore()));
        }
        Glide.with(context).load(matches.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivAwayTeam);
        holder.binding.tvAwayTeam.setText(matches.getAwayTeam().getName());
        if (matches.getAwayTeam().getScore() != null) {
            holder.binding.tvAwayScore.setText(String.valueOf(matches.getAwayTeam().getScore()));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.Extras.MATCH, matches);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MatchItemBinding binding;

        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
