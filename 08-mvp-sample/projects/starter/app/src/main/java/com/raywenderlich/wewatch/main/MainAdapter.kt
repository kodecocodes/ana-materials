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

package com.raywenderlich.wewatch.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.wewatch.R

import com.raywenderlich.wewatch.model.Movie
import com.raywenderlich.wewatch.network.RetrofitClient
import com.squareup.picasso.Picasso

import java.util.HashSet

class MainAdapter(internal var movieList: List<Movie>, internal var context: Context) : RecyclerView.Adapter<MainAdapter.MoviesHolder>() {
  // HashMap to keep track of which items were selected for deletion
  val selectedMovies = HashSet<Movie>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
    val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
    return MoviesHolder(v)
  }

  override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
    holder.titleTextView.text = movieList[position].title
    holder.releaseDateTextView.text = movieList[position].releaseDate
    if (movieList[position].posterPath.equals("")) {
      holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
    } else {
      Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movieList[position].posterPath).into(holder.movieImageView)
    }
  }

  override fun getItemCount(): Int {
    return movieList.size
  }

  inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

    internal var titleTextView: TextView
    internal var releaseDateTextView: TextView
    internal var movieImageView: ImageView
    internal var checkBox: CheckBox

    init {
      titleTextView = v.findViewById(R.id.title_textview)
      releaseDateTextView = v.findViewById(R.id.release_date_textview)
      movieImageView = v.findViewById(R.id.movie_imageview)
      checkBox = v.findViewById(R.id.checkbox)
      checkBox.setOnClickListener {
        val adapterPosition = adapterPosition
        if (!selectedMovies.contains(movieList[adapterPosition])) {
          checkBox.isChecked = true
          selectedMovies.add(movieList[adapterPosition])
        } else {
          checkBox.isChecked = false
          selectedMovies.add(movieList[adapterPosition])
        }
      }
    }

  }
}
