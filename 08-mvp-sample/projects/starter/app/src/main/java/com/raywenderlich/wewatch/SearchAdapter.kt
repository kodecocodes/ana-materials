/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.wewatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.model.Movie
import com.squareup.picasso.Picasso


class SearchAdapter(var movieList: List<Movie>, var context: Context, var listener: SearchActivity.RecyclerItemListener) : RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.item_movie_details, parent, false)

    val viewHolder = SearchMoviesHolder(view)
    view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }
    return viewHolder
  }

  override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {

    holder.titleTextView.text = movieList[position].title
    holder.releaseDateTextView.text = movieList[position].releaseDate
    holder.overviewTextView.text = movieList[position].overview

    if (movieList[position].posterPath != null) {
      Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movieList[position].posterPath).into(holder.movieImageView)
    }
  }

  override fun getItemCount(): Int {
    return movieList.size
  }

  fun getItemAtPosition(pos: Int): Movie {
    return movieList[pos]
  }

  inner class SearchMoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

    var titleTextView: TextView = v.findViewById(R.id.title_textview)
    var overviewTextView: TextView = v.findViewById(R.id.release_date_textview)
    var releaseDateTextView: TextView = v.findViewById(R.id.overview_overview)
    var movieImageView: ImageView = v.findViewById(R.id.movie_imageview)

    init {
      v.setOnClickListener { v: View ->
        listener.onItemClick(v, adapterPosition)
      }
    }
  }
}
