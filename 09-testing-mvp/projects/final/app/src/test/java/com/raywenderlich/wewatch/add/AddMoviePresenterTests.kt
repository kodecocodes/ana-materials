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

package com.raywenderlich.wewatch.add

import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AddMoviePresenterTests : BaseTest() {

  @Mock
  private lateinit var mockActivity : AddMovieContract.ViewInterface

  @Mock
  private lateinit var mockDataSource : LocalDataSource

  lateinit var addMoviePresenter : AddMoviePresenter

  @Captor
  private lateinit var movieArgumentCaptor: ArgumentCaptor<Movie>

  @Before
  fun setUp() {
    addMoviePresenter = AddMoviePresenter(viewInterface = mockActivity, dataSource = mockDataSource)
  }

  @Test
  fun testAddMovieNoTitle() {
    //Invoke
    addMoviePresenter.addMovie("", "", "")

    //Verify
    Mockito.verify(mockActivity).displayError("Movie title cannot be empty")
  }

  @Test
  fun testAddMovieWithTitle() {

    //Invoke
    addMoviePresenter.addMovie("The Lion King", "1994-05-07", "/bKPtXn9n4M4s8vvZrbw40mYsefB.jpg")

    //Verify
    Mockito.verify(mockDataSource).insert(captureArg(movieArgumentCaptor))
    assertEquals("The Lion King", movieArgumentCaptor.value.title)


    Mockito.verify(mockActivity).returnToMain()
  }

}