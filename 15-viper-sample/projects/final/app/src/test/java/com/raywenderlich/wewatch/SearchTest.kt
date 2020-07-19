package com.raywenderlich.wewatch

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.raywenderlich.wewatch.data.entity.Movie
import com.raywenderlich.wewatch.presenter.SearchPresenter
import org.junit.Before
import org.junit.Test
import ru.terrakok.cicerone.Cicerone

class SearchTest {

  private lateinit var presenter: SearchPresenter
  private lateinit var view: SearchContract.View
  private lateinit var interactor: SearchContract.Interactor

  @Before
  fun setUp() {
    val cicerone = Cicerone.create()
    val router = cicerone.router

    view = mock()
    interactor = mock()
    presenter = SearchPresenter(view, interactor, router)

  }

  @Test
  fun displayLoadingOnSearch() {
    presenter.searchMovies("")
    verify(view).showLoading()
  }

  @Test
  fun callInteractorOnSearchTitle() {
    val title = "title"
    presenter.searchMovies(title)
    verify(interactor).searchMovies(title)
  }

  @Test
  fun displayMessageAndHideLoadingOnQueryError() {
    presenter.onQueryError()
    verify(view).hideLoading()
    verify(view).hideLoading()
  }

  @Test
  fun displayMovieListAndHideLoadingOnQuerySuccess() {
    val movieList = listOf(Movie(title = "Best Movie"))
    presenter.onQuerySuccess(movieList)
    verify(view).hideLoading()
    verify(view).displayMovieList(movieList)
  }
}