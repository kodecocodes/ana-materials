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

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.raywenderlich.wewatch.App
import com.raywenderlich.wewatch.MainContract
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.data.entity.Movie
import com.raywenderlich.wewatch.interactor.MainInteractor
import com.raywenderlich.wewatch.presenter.MainPresenter
import com.raywenderlich.wewatch.view.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward


class MainActivity : BaseActivity(), MainContract.View {

  companion object {
    val TAG: String = "MainActivity"
  }

  lateinit var presenter: MainContract.Presenter
  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
  private lateinit var adapter: MovieListAdapter
  private val router: Router? by lazy { App.INSTANCE.cicerone.router }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    presenter = MainPresenter(this, MainInteractor(), router)
  }

  private val navigator: Navigator? by lazy {
    object : Navigator {
      override fun applyCommand(command: Command) {   // 2
        if (command is Forward) {
          forward(command)
        }
      }

      private fun forward(command: Forward) {
        when (command.screenKey) {
          AddMovieActivity.TAG -> startActivity(Intent(this@MainActivity, AddMovieActivity::class.java))
          else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
        }
      }
    }
  }

  override fun showLoading() {
    moviesRecyclerView.isEnabled = false
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    moviesRecyclerView.isEnabled = true
    progressBar.visibility = View.GONE
  }

  override fun onResume() {
    super.onResume()
    presenter.onViewCreated()
    App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
  }

  override fun onPause() {
    super.onPause()
    App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
  }

  override fun showMessage(msg: String) {
    toast(msg)
  }

  override fun deleteMoviesClicked() {
    presenter.deleteMovies(adapter.selectedMovies)
  }

  override fun displayMovieList(movieList: List<Movie>) {
    adapter = MovieListAdapter(movieList)
    moviesRecyclerView.adapter = adapter
  }

  override fun getToolbarInstance(): Toolbar? = toolbar

  fun goToSearchActivity(view: View) = presenter.addMovie()


  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

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
