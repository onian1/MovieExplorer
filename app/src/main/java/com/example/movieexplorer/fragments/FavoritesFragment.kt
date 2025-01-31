package com.example.movieexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieexplorer.R
import com.example.movieexplorer.SQLite.DBHelper
import com.example.movieexplorer.adapter.MovieAdapter
import com.example.movieexplorer.databinding.FragmentFavoritesBinding
import com.example.movieexplorer.models.Movie

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding;
    private lateinit var movieAdapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        movieAdapter = MovieAdapter(movieList, context, true)
        binding.recyclerView.adapter = movieAdapter

        fetchFavouriteMovies()

        return binding.root
    }

    private fun fetchFavouriteMovies(){
        if (context != null){
            val db = DBHelper(requireContext(), null)
            val movieCursor = db.getFavouriteMovie()
            movieList.clear()
            while (movieCursor.moveToNext())
                movieList.add(Movie(0, movieCursor.getString(0), "", "", movieCursor.getString(1), ""))

            movieAdapter.notifyItemRangeChanged(0, movieList.count())
        }
    }
}
