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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.view.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {

  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
  private val adapter = MovieListAdapter(mutableListOf())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    moviesRecyclerView.adapter = adapter
  }

  //TODO Implement delete
  private fun deleteMoviesClicked() {
  }

  private fun showLoading() {
    moviesRecyclerView.isEnabled = false
    progressBar.visibility = View.VISIBLE
  }

  private fun hideLoading() {
    moviesRecyclerView.isEnabled = true
    progressBar.visibility = View.GONE
  }

  override fun getToolbarInstance(): Toolbar? = toolbar

  fun goToAddActivity(view: View) = startActivity<AddMovieActivity>()

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_delete -> this.deleteMoviesClicked()
      else -> toast(getString(R.string.error))
    }
    return super.onOptionsItemSelected(item)
  }
}
