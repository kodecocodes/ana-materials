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
import com.raywenderlich.wewatch.presenter.AddPresenter
import com.raywenderlich.wewatch.view.AddView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class AddMovieActivity : BaseActivity(), AddView {

  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

  private val publishSubject: PublishSubject<Movie> = PublishSubject.create()

  private lateinit var presenter: AddPresenter

  override fun getToolbarInstance() = toolbar

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_movie)
    presenter = AddPresenter(MovieInteractor())
    presenter.bind(this)
  }

  override fun addMovieIntent() = publishSubject

  override fun onStop() {
    super.onStop()
    presenter.unbind()
  }

  override fun render(state: MovieState) {
    when (state) {
      is MovieState.FinishState -> renderFinishState()
    }
  }

  private fun renderFinishState() = startActivity(intentFor<MainActivity>().newTask().clearTask())

  fun goToSearchMovieActivity(view: View) {
    if (titleEditText.text.toString().isNotBlank()) {
      startActivity(intentFor<SearchMovieActivity>("title" to titleEditText.text.toString()))
    } else {
      showMessage("You must enter a title")
    }
  }

  fun showMessage(msg: String) {
    addLayout.snack((msg), Snackbar.LENGTH_LONG) {
      action(getString(R.string.ok)) {
      }
    }
  }

  fun addMovieClick(view: View) {
    if (titleEditText.text.toString().isNotBlank()) {
      publishSubject.onNext(Movie(title = titleEditText.text.toString(), releaseDate = yearEditText.text.toString()))
    } else {
      showMessage("You must enter a title")
    }
  }
}
