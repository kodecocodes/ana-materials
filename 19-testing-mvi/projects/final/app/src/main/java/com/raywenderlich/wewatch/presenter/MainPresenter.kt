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

package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.view.MainView
import com.raywenderlich.wewatch.domain.MovieState
import com.raywenderlich.wewatch.data.MovieInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainPresenter(private val movieInteractor: MovieInteractor) {
  private val compositeDisposable = CompositeDisposable()
  private lateinit var view: MainView

  fun bind(view: MainView) {
    this.view = view
    compositeDisposable.add(observeMovieDeleteIntent())
    compositeDisposable.add(observeMovieDisplay())
  }

  fun unbind() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.dispose()
    }
  }

  private fun observeMovieDeleteIntent() = view.deleteMovieIntent()
      .doOnNext { Timber.d("Intent: delete movie") }
      .subscribeOn(AndroidSchedulers.mainThread())
      .observeOn(Schedulers.io())
      .flatMap<Unit> { movieInteractor.deleteMovie(it) }
      .subscribe()

  private fun observeMovieDisplay() = view.displayMoviesIntent()
      .doOnNext { Timber.d("Intent: display movies intent") }
      .flatMap<MovieState> { movieInteractor.getMovieList() }
      .startWith(MovieState.LoadingState)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { view.render(it) }
}