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

package com.raywenderlich.wewatch.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.wewatch.domain.MovieState
import com.raywenderlich.wewatch.view.MainView
import com.raywenderlich.wewatch.R
import com.raywenderlich.wewatch.data.MovieInteractor
import com.raywenderlich.wewatch.data.model.Movie
import com.raywenderlich.wewatch.presenter.MainPresenter
import com.raywenderlich.wewatch.view.adapter.MovieListAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import timber.log.Timber

class MainActivity : BaseActivity(), MainView {
  override fun displayMoviesIntent(): Observable<Unit> = Observable.just(Unit)

  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

  override fun getToolbarInstance(): Toolbar? = toolbar

  private lateinit var presenter: MainPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    moviesRecyclerView.adapter = MovieListAdapter(emptyList())
    presenter = MainPresenter(MovieInteractor())
    presenter.bind(this)
  }

  override fun deleteMovieIntent(): Observable<Movie> {
    val observable = Observable.create<Movie> { emitter ->
      // Add the functionality to swipe items in the
      // recycler view to delete that item
      val helper = ItemTouchHelper(
          object : ItemTouchHelper.SimpleCallback(
              0,
              ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
          ) {
            // We are not implementing onMove() in this app
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
              return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
              val position = viewHolder.getAdapterPosition()
              val movie = (moviesRecyclerView.adapter as MovieListAdapter).getMoviesAtPosition(position)
              emitter.onNext(movie)
            }
          })

      helper.attachToRecyclerView(moviesRecyclerView)
    }
    return observable
  }

  fun goToAddActivity(view: View) = startActivity<AddMovieActivity>()

  override fun render(state: MovieState) {
    when (state) {
      is MovieState.LoadingState -> renderLoadingState()
      is MovieState.DataState -> renderDataState(state)
      is MovieState.ErrorState -> renderErrorState(state)
    }
  }

  private fun renderLoadingState() {
    Timber.d("Render: loading state")
    moviesRecyclerView.isEnabled = false
    progressBar.visibility = View.VISIBLE
  }

  private fun renderDataState(dataState: MovieState.DataState) {
    Timber.d("Render: data state")
    progressBar.visibility = View.GONE
    moviesRecyclerView.apply {
      isEnabled = true
      (adapter as MovieListAdapter).setMovies(dataState.data)
    }
  }

  private fun renderErrorState(dataState: MovieState.ErrorState){
    Timber.d("Render: Error State")
    longToast(dataState.data)
  }

  override fun onStop() {
    presenter.unbind()
    super.onStop()
  }
}
