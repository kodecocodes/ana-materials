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

package com.raywenderlich.wewatch.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.action
import com.raywenderlich.wewatch.data.model.Movie
import com.raywenderlich.wewatch.snack
import com.raywenderlich.wewatch.view.adapters.SearchListAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SearchMovieActivity : BaseActivity() {

  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
  private var adapter = SearchListAdapter(mutableListOf()) { movie -> displayConfirmation(movie) }
  private lateinit var title: String

  override fun getToolbarInstance(): Toolbar? = toolbar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    intent?.extras?.getString("title")?.let {
      title = it
    }
    searchRecyclerView.adapter = adapter
  }

  private fun showLoading() {
    searchProgressBar.visibility = View.VISIBLE
    searchRecyclerView.isEnabled = false
  }

  private fun hideLoading() {
    searchProgressBar.visibility = View.GONE
    searchRecyclerView.isEnabled = true
  }

  private fun showMessage() {
    searchLayout.snack(getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE) {
      action(getString(R.string.ok)) {
      }
    }
  }

  private fun displayConfirmation(movie: Movie) {
    searchLayout.snack("Add ${movie.title} to your list?", Snackbar.LENGTH_LONG) {
      action(getString(R.string.ok)) {
        startActivity(intentFor<MainActivity>().newTask().clearTask())
      }
    }
  }
}
