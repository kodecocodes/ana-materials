package com.raywenderlich.wewatch

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.raywenderlich.wewatch.data.entity.Movie
import com.raywenderlich.wewatch.presenter.MainPresenter
import org.junit.Before
import org.junit.Test
import ru.terrakok.cicerone.Cicerone
import java.util.*

class MainTest {

  private lateinit var presenter: MainPresenter
  private lateinit var view: MainContract.View
  private lateinit var interactor: MainContract.Interactor

  @Before
  fun setup() {
    val cicerone = Cicerone.create()

    view = mock()
    interactor = mock()
    presenter = MainPresenter(view, interactor, cicerone.router)

  }

  @Test
  fun createPresenterCallsViewShowLoading() {
    presenter.onViewCreated()
    verify(view).showLoading()
  }

  @Test
  fun deleteMovieCallsInteractorDelete() {
    val selectedMovies = HashSet<Movie>()
    selectedMovies.add(Movie())
    presenter.deleteMoviesClick(selectedMovies)
    verify(interactor).delete(any())
  }

  @Test
  fun loadMovies() {
    presenter.onViewCreated()
    verify(interactor).loadMovieList()
  }

  @Test
  fun displayMessageOnQueryError() {
    presenter.onQueryError()
    verify(view).showMessage(any())
  }

  @Test
  fun displayMoviesOnQuerySuccess() {
    val movieList = listOf(Movie())
    presenter.onQuerySuccess(movieList)
    verify(view).displayMovieList(movieList)
  }

  @Test
  fun presenterQueryErrorNeverCallsDisplayMovieList() {
    val movieList = listOf(Movie())
    presenter.onQueryError()
    verify(view, never()).displayMovieList(movieList)
  }

}