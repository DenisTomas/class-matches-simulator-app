package domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Matches(
    @SerializedName("description")
    val description: String,
    @SerializedName("place")
    val place: Place,
    @SerializedName("home team")
    val homeTeam: Team,
    @SerializedName("away team")
    val awayTeam: Team
 ) : Parcelable