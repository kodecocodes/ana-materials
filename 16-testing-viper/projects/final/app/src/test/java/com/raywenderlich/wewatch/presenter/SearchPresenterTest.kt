package com.raywenderlich.wewatch.presenter

import com.nhaarman.mockito_kotlin.verify
import com.raywenderlich.wewatch.SearchContract
import com.raywenderlich.wewatch.data.entity.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Cicerone

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTest {

  private lateinit var presenter: SearchPresenter
  @Mock
  private lateinit var view: SearchContract.View
  @Mock
  private lateinit var interactor: SearchContract.Interactor

  @Before
  fun setUp() {
    val cicerone = Cicerone.create()
    val router = cicerone.router

    presenter = SearchPresenter(view, interactor, router)
  }

  @Test
  fun displayMovieListAndHideLoadingOnQuerySuccess() {
    val movieList = listOf(Movie(title = "Best Movie"))
    presenter.onQuerySuccess(movieList)
    verify(view).hideLoading()
    verify(view).displayMovieList(movieList)
  }
}