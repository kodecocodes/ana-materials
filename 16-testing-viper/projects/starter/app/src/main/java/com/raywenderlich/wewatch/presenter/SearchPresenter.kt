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

package com.raywenderlich.wewatch.presenter

import androidx.lifecycle.Observer
import com.raywenderlich.wewatch.SearchContract
import com.raywenderlich.wewatch.data.entity.Movie
import com.raywenderlich.wewatch.view.activities.MainActivity
import com.raywenderlich.wewatch.view.activities.SearchMovieActivity
import ru.terrakok.cicerone.Router

class SearchPresenter(private var view: SearchContract.View?, private var interactor: SearchContract.Interactor?, val router: Router?) : SearchContract.Presenter, SearchContract.InteractorOutput {

  override fun searchMovies(title: String) {
    view?.showLoading()
    interactor?.searchMovies(title)?.observe(view as SearchMovieActivity, Observer { movieLiest ->
      if (movieLiest == null) {
        onQueryError()
      } else {
        onQuerySuccess(movieLiest)
      }
    })
  }

  override fun addMovieClicked(movie: Movie?) {
    interactor?.addMovie(movie)
    router?.navigateTo(MainActivity.TAG)
  }

  override fun movieClicked(movie: Movie?) {
    view?.displayConfirmation(movie)
  }

  override fun onDestroy() {
    view = null
    interactor = null
  }

  override fun onQuerySuccess(data: List<Movie>) {
    view?.hideLoading()
  }

  override fun onQueryError() {
    view?.hideLoading()
    view?.showMessage("Error")
  }

}