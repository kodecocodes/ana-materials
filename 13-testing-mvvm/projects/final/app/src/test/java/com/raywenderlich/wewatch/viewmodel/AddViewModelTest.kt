package com.raywenderlich.wewatch.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.wewatch.data.MovieRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest{

  private lateinit var addViewModel: AddViewModel

  @Mock
  lateinit var repository: MovieRepository

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  @Before
  fun setup(){
    addViewModel = AddViewModel(repository)
  }

  @Test
  fun cantSaveMovieWithoutTitle(){
    addViewModel.title.set("")
    addViewModel.releaseDate.set("")
    val canSaveMovie = addViewModel.canSaveMovie()
    assertEquals(false, canSaveMovie)
  }

  @Test
  fun cantSaveMovieWithoutDate(){
    addViewModel.title.set("Awesome Movie I")
    addViewModel.releaseDate.set("")
    val canSaveMovie = addViewModel.canSaveMovie()
    assertEquals(false, canSaveMovie)
  }

  @Test
  fun isMovieProperlySaved(){
    addViewModel.title.set("Awesome Movie II")
    addViewModel.releaseDate.set("1994")
    addViewModel.saveMovie()
    assertEquals(true, addViewModel.getSaveLiveData().value)
  }
}