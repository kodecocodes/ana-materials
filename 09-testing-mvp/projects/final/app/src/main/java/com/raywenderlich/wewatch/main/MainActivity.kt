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

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

  private lateinit var moviesRecyclerView: RecyclerView
  private lateinit var adapter: MainAdapter
  private lateinit var fab: FloatingActionButton
  private lateinit var noMoviesLayout: LinearLayout

  private lateinit var mainPresenter: MainContract.PresenterInterface

  private val TAG = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setupPresenter()
    setupViews()
  }

  override fun onStart() {
    super.onStart()
    mainPresenter.getMyMoviesList()
  }

  override fun onStop() {
    super.onStop()
    mainPresenter.stop()
  }

  private fun setupPresenter() {
    val dataSource = LocalDataSource(application)
    mainPresenter = MainPresenter(this, dataSource)
  }

  private fun setupViews() {
    moviesRecyclerView = findViewById(R.id.movies_recyclerview)
    moviesRecyclerView.layoutManager = LinearLayoutManager(this)
    fab = findViewById(R.id.fab)
    noMoviesLayout = findViewById(R.id.no_movies_layout)
    supportActionBar?.title = "Movies to Watch"
  }

  override fun displayMovies(movieList: List<Movie>) {
    adapter = MainAdapter(movieList, this@MainActivity)
    moviesRecyclerView.adapter = adapter

    moviesRecyclerView.visibility = VISIBLE
    noMoviesLayout.visibility = INVISIBLE
  }

  override fun displayNoMovies() {
    Log.d(TAG, "No movies to display")

    moviesRecyclerView.visibility = INVISIBLE
    noMoviesLayout.visibility = VISIBLE
  }

  //fab onClick
  fun goToAddMovieActivity(v: View) {
    val myIntent = Intent(this@MainActivity, AddMovieActivity::class.java)
    startActivityForResult(myIntent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == ADD_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      showToast("Movie successfully added.")
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
      mainPresenter.onDeleteTapped(adapter.selectedMovies)
    }

    return super.onOptionsItemSelected(item)
  }

  override fun showToast(string: String) {
    Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
  }

  override fun displayError(e: String) {
    showToast(e)
  }

  companion object {
    const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
  }

}