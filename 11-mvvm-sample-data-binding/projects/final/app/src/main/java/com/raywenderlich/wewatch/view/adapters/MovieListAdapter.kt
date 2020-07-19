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

package com.raywenderlich.wewatch.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.data.model.Movie
import com.raywenderlich.wewatch.data.net.RetrofitClient
import com.raywenderlich.wewatch.databinding.ItemMovieMainBinding
import com.raywenderlich.wewatch.setImageUrl
import java.util.*

class MovieListAdapter(private val movies: MutableList<Movie>)
  : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

  val selectedMovies = HashSet<Movie>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ItemMovieMainBinding>(layoutInflater, R.layout.item_movie_main,parent, false)
    return MovieHolder(binding)
  }

  override fun getItemCount(): Int = movies.size

  override fun onBindViewHolder(holder: MovieHolder, position: Int) {
    val movie = movies[position]
    holder.binding.movie = movie
    if (movie.posterPath != null) {
      holder.binding.movieImageView.setImageUrl(RetrofitClient.TMDB_IMAGEURL + movie.posterPath)
    } else {
      holder.binding.movieImageView.setImageUrl(R.drawable.ic_local_movies_gray)
    }

    holder.binding.checkbox.isChecked = selectedMovies.contains(movie)

    holder.binding.checkbox.setOnCheckedChangeListener{ checkbox, isChecked ->
      if (!selectedMovies.contains(movie) && isChecked) {
        selectedMovies.add(movies[position])
      }else{
        selectedMovies.remove(movies[position])
      }
    }
  }

  fun setMovies(movieList: List<Movie>) {
    this.movies.clear()
    this.movies.addAll(movieList)
    notifyDataSetChanged()
  }

  inner class MovieHolder(val binding: ItemMovieMainBinding) : RecyclerView.ViewHolder(binding.root)
}