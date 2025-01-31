import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    val results: List<SearchMovie>
)

data class SearchMovie(
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("poster_path")
    val posterPath: String
)
