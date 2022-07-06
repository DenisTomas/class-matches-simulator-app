package ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import aula.dio.simulator.aula.dio.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import domain.Matches

class DetailActivity : AppCompatActivity() {

    object Extras {
        const val MATCH = "EXTRA>MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Matches>(Extras.MATCH)?.let {
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.name

            binding.tvDescription.text = it.description

            Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.homeTeam.name
            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if (it.homeTeam.score != null) {
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            }

            Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.awayTeam.name
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
            if (it.awayTeam.score != null) {
                binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
            }
        }
    }
}
