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
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.wewatch.*
import com.raywenderlich.wewatch.data.MovieInteractor
import com.raywenderlich.wewatch.data.model.Movie
import com.raywenderlich.wewatch.domain.MovieState
import com.raywenderlich.wewatch.presenter.SearchPresenter
import com.raywenderlich.wewatch.view.SearchView
import com.raywenderlich.wewatch.view.adapter.SearchListAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_search_movie.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.newTask
import timber.log.Timber

class SearchMovieActivity : BaseActivity(), SearchView {

  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

  override fun getToolbarInstance(): Toolbar? = toolbar

  private lateinit var presenter: SearchPresenter

  private val publishSubject: PublishSubject<Movie> = PublishSubject.create<Movie>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_movie)
    searchRecyclerView.adapter = SearchListAdapter(emptyList())
    presenter = SearchPresenter(MovieInteractor())
    presenter.bind(this)

  }

  override fun displayMoviesIntent(): Observable<String> = Observable.just(intent.extras.getString("title"))


  override fun addMovieIntent(): Observable<Movie> = (searchRecyclerView.adapter as SearchListAdapter).getViewClickObservable()

  override fun confirmIntent(): Observable<Movie> = publishSubject


  override fun onStop() {
    super.onStop()
    presenter.unbind()
  }

  override fun render(state: MovieState) {
    when (state) {
      is MovieState.LoadingState -> renderLoadingState()
      is MovieState.DataState -> renderDataState(state)
      is MovieState.ErrorState -> renderErrorState(state)
      is MovieState.ConfirmationState -> renderConfirmationState(state)
      is MovieState.FinishState -> renderFinishState()
    }
  }

  private fun renderFinishState(){
    Timber.d("Render: finish state")
    startActivity(intentFor<MainActivity>().newTask().clearTask())
  }


  private fun renderLoadingState() {
    Timber.d("Render: loading state")
    searchRecyclerView.isEnabled = false
    searchProgressBar.visibility = View.VISIBLE
  }

  private fun renderConfirmationState(confirmationState: MovieState.ConfirmationState) {
    Timber.d("Render: confirm state")
    searchLayout.snack("Add ${confirmationState.movie.title} to your list?", Snackbar.LENGTH_LONG) {
      action(getString(R.string.ok)) {
        publishSubject.onNext(confirmationState.movie)
      }
    }
  }

  private fun renderDataState(dataState: MovieState.DataState) {
    Timber.d("Render: data state")
    searchProgressBar.visibility = View.GONE
    searchRecyclerView.apply {
      isEnabled = true
      (adapter as SearchListAdapter).setMovies(dataState.data)
    }
  }

  private fun renderErrorState(errorState: MovieState.ErrorState) {
    Timber.d("Render: error state")
    searchProgressBar.visibility = View.GONE
    longToast(errorState.data)
  }
}
