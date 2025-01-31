package com.example.movieexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieexplorer.R
import com.example.movieexplorer.adapter.MovieAdapter
import com.example.movieexplorer.models.Movie
import com.example.movieexplorer.models.MovieResponse
import com.example.movieexplorer.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        movieAdapter = MovieAdapter(movieList)
        recyclerView.adapter = movieAdapter

        fetchMovies()

        return rootView
    }

    private fun fetchMovies() {
        val apiService = RetrofitInstance.apiService
        val call = apiService.getMovies()
        call.enqueue(object : Callback<MovieResponse> { // Use MovieResponse here
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse != null) {
                        movieList.clear()
                        movieList.addAll(movieResponse.results) // Access 'results' from MovieResponse
                        movieAdapter.notifyItemRangeChanged(0, movieResponse.results.size)
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(context, "Failed to load movies: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

