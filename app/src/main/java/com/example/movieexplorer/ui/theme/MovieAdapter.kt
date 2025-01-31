package com.example.movieexplorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieexplorer.databinding.ItemMovieBinding
import com.example.movieexplorer.models.Movie

class MovieAdapter(private val movieList: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieDescription.text = movie.description

            // Build the full URL for the image
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            // Use Glide to load the image into the ImageView
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.moviePoster) // Make sure this ImageView is in your layout
        }
    }
}
