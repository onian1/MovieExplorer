package com.example.movieexplorer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieexplorer.R
import com.example.movieexplorer.SQLite.DBHelper
import com.example.movieexplorer.databinding.ItemMovieBinding
import com.example.movieexplorer.models.Movie

class MovieAdapter(private val movieList: List<Movie>, private val context: Context?) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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
            if (context != null) {
                val db = DBHelper(context, null)
                if (db.getFavouriteMovie(movie.title).count == 1) {
                    binding.setAsFavouriteBtn.setImageResource(R.drawable.baseline_favorite_24)
                }else{
                    binding.setAsFavouriteBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                db.close()

                binding.setAsFavouriteBtn.setOnClickListener {
                    val dbLocal = DBHelper(context, null)
                    if (dbLocal.getFavouriteMovie(movie.title).count == 1) {
                        dbLocal.removeFavouriteMovie(movie.title)
                        binding.setAsFavouriteBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                        Toast.makeText(
                            context,
                            "This movie has been removed from the favourites.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        dbLocal.addFavouriteMovie(movie.title, movie.poster_path)
                        binding.setAsFavouriteBtn.setImageResource(R.drawable.baseline_favorite_24)
                        Toast.makeText(
                            context,
                            "This movie has been added to the favourites.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dbLocal.close()
                }
            }

            // Build the full URL for the image
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            // Use Glide to load the image into the ImageView
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.moviePoster) // Make sure this ImageView is in your layout
        }
    }
}
