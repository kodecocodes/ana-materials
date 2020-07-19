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

package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.RxImmediateSchedulerRule
import com.raywenderlich.wewatch.model.Movie
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {
  @Rule
  @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

  @Mock
  private lateinit var mockActivity : SearchContract.ViewInterface

  @Mock
  private val mockDataSource = RemoteDataSource()

  lateinit var searchPresenter: SearchPresenter

  @Before
  fun setUp() {
    searchPresenter = SearchPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
  }

  @Test
  fun testSearchMovie() {
    //Set up
    val myDummyResponse = dummyResponse
    Mockito.doReturn(Observable.just(myDummyResponse)).`when`(mockDataSource).searchResultsObservable(anyString())

    //Invoke
    searchPresenter.getSearchResults("The Lion King")

    //Assert
    Mockito.verify(mockActivity).displayResult(myDummyResponse)
  }

  @Test
  fun testSearchMovieError() {
    //Set up
    Mockito.doReturn(
        Observable.error<Throwable>(Throwable("Something went wrong"))).`when`(mockDataSource).searchResultsObservable(anyString())

    //Invoke
    searchPresenter.getSearchResults("The Lion King")

    //Assert
    Mockito.verify(mockActivity).displayError("Error fetching Movie Data")
  }

  private val dummyResponse: TmdbResponse
    get() {
      val dummyMovieList = ArrayList<Movie>()
      dummyMovieList.add(Movie("Title1", "ReleaseDate1", "PosterPath1"))
      dummyMovieList.add(Movie("Title2", "ReleaseDate2", "PosterPath2"))
      dummyMovieList.add(Movie("Title3", "ReleaseDate3", "PosterPath3"))
      dummyMovieList.add(Movie("Title4", "ReleaseDate4", "PosterPath4"))

      return TmdbResponse(1, 4, 5, dummyMovieList)
    }
}