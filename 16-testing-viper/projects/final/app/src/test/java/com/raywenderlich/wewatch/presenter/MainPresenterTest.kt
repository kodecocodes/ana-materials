package com.raywenderlich.wewatch.presenter

import com.nhaarman.mockito_kotlin.verify
import com.raywenderlich.wewatch.MainContract
import com.raywenderlich.wewatch.data.entity.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Cicerone

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

  private lateinit var presenter: MainPresenter
  @Mock
  private lateinit var view: MainContract.View
  @Mock
  private lateinit var interactor: MainContract.Interactor

  @Before
  fun setup() {
    val cicerone = Cicerone.create()
    presenter = MainPresenter(view, interactor, cicerone.router)
  }

  @Test
  fun displayMoviesOnQuerySuccess() {
    val movieList = listOf(Movie())
    presenter.onQuerySuccess(movieList)
    verify(view).displayMovieList(movieList)
  }

  @Test
  fun loadMovies() {
    presenter.onViewCreated()
    verify(interactor).loadMovieList()
  }
}