/*
 *
 *  Copyright (c) 2018 Razeware LLC
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
 *
 */

package com.raywenderlich.wewatch.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.data.model.Movie
import com.raywenderlich.wewatch.data.net.RetrofitClient
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_movie_search.view.*

class SearchListAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<SearchListAdapter.MovieHolder>() {

  val publishSubject: PublishSubject<Movie> = PublishSubject.create<Movie>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movie_search, parent, false)
    return MovieHolder(view)
  }

  override fun getItemCount(): Int = movies.size

  override fun onBindViewHolder(holder: MovieHolder, position: Int) = holder.bind(movies[position], position)

  fun setMovies(movieList: List<Movie>) {
    movies = movieList
    notifyDataSetChanged()
  }

  fun getViewClickObservable(): Observable<Movie> {
    return publishSubject
  }

  inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(movie: Movie, position: Int) = with(view) {
      searchTitleTextView.text = movie.title
      searchReleaseDateTextView.text = movie.releaseDate
      view.setOnClickListener {
          publishSubject.onNext(movies[position])
      }
      if (movie.posterPath != null)
        Picasso.get().load(RetrofitClient.TMDB_IMAGEURL + movie.posterPath).into(searchImageView)
      else {
        searchImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_local_movies_gray, null))
      }
    }
  }
}