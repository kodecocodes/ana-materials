/*
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
 */

package com.raywenderlich.wewatch

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
  val snack = Snackbar.make(this, message, length)
  snack.show()
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_INDEFINITE, f: Snackbar.() -> Unit) {
  val snack = Snackbar.make(this, message, length)
  snack.f()
  snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
  setAction(action, listener)
  color?.let { setActionTextColor(color) }
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
  Picasso.get().load(url).into(this)
}

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(int: Int) {
  this.setImageDrawable(resources.getDrawable(int,null))
}