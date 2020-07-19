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
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.wewatch.*
import com.raywenderlich.wewatch.interactor.AddInteractor
import com.raywenderlich.wewatch.presenter.AddPresenter
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class AddMovieActivity : BaseActivity(), AddContract.View {

  var presenter: AddContract.Presenter? = null
  private val router: Router? by lazy { App.INSTANCE.cicerone.router }
  private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

  companion object {
    val TAG: String = "AddMovieActivity"
  }

  private val navigator: Navigator? by lazy {
    object : Navigator {
      override fun applyCommand(command: Command) {
        if (command is Back) {
          back()
        }
        if (command is Forward) {
          forward(command)
        }
      }

      private fun forward(command: Forward) {
        when (command.screenKey) {
          SearchMovieActivity.TAG -> startActivity(Intent(this@AddMovieActivity, SearchMovieActivity::class.java)
              .putExtra("title", titleEditText.text.toString()))
          MainActivity.TAG -> startActivity(Intent(this@AddMovieActivity, MainActivity::class.java)
              .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
          else -> Log.e("Cicerone", "Unknown screen: " + command.screenKey)
        }
      }

      private fun back() {
        finish()
      }
    }
  }

  override fun getToolbarInstance(): Toolbar? = toolbar

  override fun searchMovieClicked(view: View) {
    presenter?.searchMovies(titleEditText.text.toString())
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add)
    presenter = AddPresenter(this, AddInteractor(), router)
  }

  override fun showMessage(msg: String) {
    addLayout.snack((msg), Snackbar.LENGTH_LONG) {
      action(getString(R.string.ok)) {
      }
    }
  }

  override fun onResume() {
    super.onResume()
    App.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter?.onDestroy()
  }

  override fun onPause() {
    super.onPause()
    App.INSTANCE.cicerone.navigatorHolder.removeNavigator()
  }

  override fun addMovieClicked(view: View) {
    presenter?.addMovies(titleEditText.text.toString(), yearEditText.text.toString())
  }
}
