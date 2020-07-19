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

import com.raywenderlich.wewatch.BaseTest
import com.raywenderlich.wewatch.RxImmediateSchedulerRule
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.ArrayList
import kotlin.collections.HashSet

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests : BaseTest() {
  @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

  @Mock
  private lateinit var mockActivity : MainContract.ViewInterface

  @Mock
  private lateinit var mockDataSource : LocalDataSource

  lateinit var mainPresenter : MainPresenter

  @Before
  fun setUp() {
    mainPresenter = MainPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
  }

/**
  Tests for getting movies
 */

  @Test
  fun testGetMyMoviesList() {
    //Set up
    val myDummyMovies = dummyAllMovies
    Mockito.doReturn(Observable.just(myDummyMovies)).`when`(mockDataSource).allMovies

    //Invoke
    mainPresenter.getMyMoviesList()

    //Assert
    Mockito.verify(mockDataSource).allMovies
    Mockito.verify(mockActivity).displayMovies(myDummyMovies)
  }

  @Test
  fun testGetMyMoviesListWithNoMovies() {
    //Set up
    Mockito.doReturn(Observable.just(ArrayList<Movie>())).`when`(mockDataSource).allMovies

    //Invoke
    mainPresenter.getMyMoviesList()

    //Assert
    Mockito.verify(mockDataSource).allMovies
    Mockito.verify(mockActivity).displayNoMovies()
  }

/**
  Tests for deleting movies
 */

  @Test
  fun testDeleteSingle() {

    //Invoke
    val myDeletedHashSet = deletedHashSetSingle
    mainPresenter.onDeleteTapped(myDeletedHashSet)

    //Assert
    for (movie in myDeletedHashSet) {
      Mockito.verify(mockDataSource).delete(movie)
    }

    Mockito.verify(mockActivity).showToast("Movie deleted")
  }

  @Test
  fun testDeleteMultiple() {

    //Invoke
    val myDeletedHashSet = deletedHashSetMultiple
    mainPresenter.onDeleteTapped(myDeletedHashSet)

    //Assert
    for (movie in myDeletedHashSet) {
      Mockito.verify(mockDataSource).delete(movie)
    }

    Mockito.verify(mockActivity).showToast("Movies deleted")
  }


  //Helper functions

  private val dummyAllMovies: ArrayList<Movie>
    get() {
      val dummyMovieList = ArrayList<Movie>()
      dummyMovieList.add(Movie("Title1", "ReleaseDate1", "PosterPath1"))
      dummyMovieList.add(Movie("Title2", "ReleaseDate2", "PosterPath2"))
      dummyMovieList.add(Movie("Title3", "ReleaseDate3", "PosterPath3"))
      dummyMovieList.add(Movie("Title4", "ReleaseDate4", "PosterPath4"))
      return dummyMovieList
    }

  private val deletedHashSetSingle: HashSet<Movie>
    get() {
      val deletedHashSet = HashSet<Movie>()
      deletedHashSet.add(dummyAllMovies[2])

      return deletedHashSet
    }

  private val deletedHashSetMultiple: HashSet<Movie>
    get() {
      val deletedHashSet = HashSet<Movie>()
      deletedHashSet.add(dummyAllMovies[1])
      deletedHashSet.add(dummyAllMovies[3])

      return deletedHashSet
    }
}
