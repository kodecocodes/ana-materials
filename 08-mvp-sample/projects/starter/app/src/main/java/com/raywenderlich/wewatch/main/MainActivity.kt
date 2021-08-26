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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.add.AddMovieActivity
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

  private lateinit var moviesRecyclerView: RecyclerView
  private lateinit var adapter: MainAdapter
  private lateinit var fab: FloatingActionButton
  private lateinit var noMoviesLayout: LinearLayout

  private lateinit var dataSource: LocalDataSource
  private lateinit var mainPresenter: MainContract.PresenterInterface
  private val compositeDisposable = CompositeDisposable()

  private val TAG = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupPresenter()
    setupViews()
  }

  override fun onStart() {
    super.onStart()
    dataSource = LocalDataSource(application)
    getMyMoviesList()
  }

  override fun onStop() {
    super.onStop()
    compositeDisposable.clear()
  }

  private fun setupViews() {
    moviesRecyclerView = findViewById(R.id.movies_recyclerview)
    moviesRecyclerView.layoutManager = LinearLayoutManager(this)
    adapter = MainAdapter(arrayListOf(), this@MainActivity)
    moviesRecyclerView.adapter = adapter
    fab = findViewById(R.id.fab)
    noMoviesLayout = findViewById(R.id.no_movies_layout)
    supportActionBar?.title = "Movies to Watch"

  }

  private fun setupPresenter() {
    val dataSource = LocalDataSource(application)
    mainPresenter = MainPresenter(this, dataSource)
  }

  private fun getMyMoviesList() {
    val myMoviesDisposable = myMoviesObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(observer)

    compositeDisposable.add(myMoviesDisposable)
  }

  private val myMoviesObservable: Observable<List<Movie>>
    get() = dataSource.allMovies


  private val observer: DisposableObserver<List<Movie>>
    get() = object : DisposableObserver<List<Movie>>() {

      override fun onNext(movieList: List<Movie>) {
        displayMovies(movieList)
      }

      override fun onError(@NonNull e: Throwable) {
        Log.e(TAG, "Error fetching movie list", e)
        displayError("Error fetching movie list")
      }

      override fun onComplete() {
        Log.d(TAG, "Completed")
      }
    }

  fun displayMovies(movieList: List<Movie>?) {
    if (movieList == null || movieList.size == 0) {
      Log.d(TAG, "No movies to display.")
      moviesRecyclerView.visibility = INVISIBLE
      noMoviesLayout.visibility = VISIBLE
    } else {
      adapter.movieList = movieList
      adapter.notifyDataSetChanged()

      moviesRecyclerView.visibility = VISIBLE
      noMoviesLayout.visibility = INVISIBLE
    }
  }

  //fab onClick
  fun goToAddMovieActivity(v: View) {
    val myIntent = Intent(this@MainActivity, AddMovieActivity::class.java)
    startActivityForResult(myIntent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == ADD_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      displayMessage("Movie successfully added.")
    } else {
      displayError("Movie could not be added.")
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.deleteMenuItem) {
      for (movie in adapter.selectedMovies) {
        dataSource.delete(movie)
      }
      if (adapter.selectedMovies.size == 1) {
        displayMessage("Movie deleted.")
      } else if (adapter.selectedMovies.size > 1) {
        displayMessage("Movies deleted.")
      }
    }

    return super.onOptionsItemSelected(item)
  }

  fun displayMessage(message: String) {
    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
  }

  fun displayError(message: String) {
    displayMessage(message)
  }

  companion object {
    const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
  }


}