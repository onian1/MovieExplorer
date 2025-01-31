package com.example.movieexplorer.fragments

import MovieSearchResponse
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.movieexplorer.BuildConfig
import com.example.movieexplorer.R
import com.example.movieexplorer.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var movieNam: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieScore: TextView
    private lateinit var moviePoster: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        searchView = rootView.findViewById(R.id.search_view)
        movieNam = rootView.findViewById(R.id.movie_name)
        movieDescription = rootView.findViewById(R.id.movie_description)
        movieScore = rootView.findViewById(R.id.movie_score)
        moviePoster = rootView.findViewById(R.id.movie_poster)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    fetchMovie(it) // Call the API when user submits the search
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // You can add auto-search functionality here if needed
                return true
            }
        })

        return rootView
    }

    private fun fetchMovie(movieName: String) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.searchMovies(movieName, BuildConfig.TMDB_API_KEY)
        call.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse != null && movieResponse.results.isNotEmpty()) {
                        // Movie found - update UI with movie details
                        val movie = movieResponse.results[0]
                        movieNam.text = movie.title
                        movieDescription.text = movie.overview
                        movieScore.text = "Rating: ${movie.voteAverage}"
                        val posterPath = movie.posterPath
                        Glide.with(this@SearchFragment)
                            .load("https://image.tmdb.org/t/p/w500$posterPath")
                            .into(moviePoster)
                    } else {
                        // No movies found in the response: clear UI and notify user
                        clearPage()
                        Toast.makeText(context, "Movie not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Response not successful: clear UI and notify user with error code
                    clearPage()
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                // Network or other error: clear UI and notify user
                clearPage()
                Toast.makeText(context, "Failed to fetch movie: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Utility function to clear UI elements
    private fun clearPage() {
        movieNam.text = ""
        movieDescription.text = ""
        movieScore.text = ""
        // Clears the image in the ImageView if any exists.
        Glide.with(this@SearchFragment).clear(moviePoster)
    }


}
