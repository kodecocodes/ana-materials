package com.raywenderlich.wewatch

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.raywenderlich.wewatch.data.entity.Movie
import com.raywenderlich.wewatch.presenter.AddPresenter
import org.junit.Before
import org.junit.Test
import ru.terrakok.cicerone.Cicerone

class AddTest {

  private lateinit var presenter: AddPresenter
  private lateinit var view: AddContract.View
  private lateinit var interactor: AddContract.Interactor

  @Before
  fun setup() {

    val cicerone = Cicerone.create()
    val router = cicerone.router

    view = mock()
    interactor = mock()
    presenter = AddPresenter(view, interactor, router)
  }

  @Test
  fun displayMessageWithBlankTitle() {
    presenter.addMovies("", "")
    verify(view).showMessage(any())
  }

  @Test
  fun neverCallAddMovieWithBlankTitle() {
    presenter.addMovies("", "")
    verify(interactor, never()).addMovie(any())
  }

  @Test
  fun addMovieWithTitle() {
    val movie = Movie(title = "Test Movie", releaseDate = "1991")
    presenter.addMovies(movie.title!!, movie.releaseDate!!)
    verify(interactor).addMovie(movie)
  }

}

