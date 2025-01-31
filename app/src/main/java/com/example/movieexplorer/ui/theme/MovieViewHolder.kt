package com.example.movieexplorer.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieexplorer.R
import com.example.movieexplorer.models.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)

    fun bind(movie: Movie) {
        titleTextView.text = movie.title
    }
}
