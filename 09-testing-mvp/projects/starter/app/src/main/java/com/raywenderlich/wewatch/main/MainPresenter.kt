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


import android.util.Log
import com.raywenderlich.wewatch.model.Movie
import com.raywenderlich.wewatch.model.LocalDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import java.util.HashSet

class MainPresenter(private var viewInterface: MainContract.ViewInterface, private var dataSource: LocalDataSource) : MainContract.PresenterInterface {
  private val TAG = "MainPresenter"
  private val compositeDisposable = CompositeDisposable()

  val myMoviesObservable: Observable<List<Movie>>
    get() = dataSource.allMovies

  val observer: DisposableObserver<List<Movie>>
    get() = object : DisposableObserver<List<Movie>>() {

      override fun onNext(movieList: List<Movie>) {
        if (movieList == null || movieList.size == 0) {
          viewInterface.displayNoMovies()
        } else {
          viewInterface.displayMovies(movieList)
        }
      }

      override fun onError(@NonNull e: Throwable) {
        Log.d(TAG, "Error$e")
        e.printStackTrace()
        viewInterface.displayError("Error fetching movie list")
      }

      override fun onComplete() {
        //Log.d(TAG, "Completed")
      }
    }

  override fun getMyMoviesList() {
    val myMoviesDisposable = myMoviesObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(observer)

    compositeDisposable.add(myMoviesDisposable)
  }


  override fun onDeleteTapped(selectedMovies: HashSet<*>) {
    for (movie in selectedMovies) {
      dataSource.delete(movie as Movie)
    }
    if (selectedMovies.size == 1) {
      viewInterface.showToast("Movie deleted")
    } else if (selectedMovies.size > 1) {
      viewInterface.showToast("Movies deleted")
    }
  }

  override fun stop() {
    compositeDisposable.clear()
  }

}
