package com.raywenderlich.wewatch.viewmodel

import com.raywenderlich.wewatch.data.MovieRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest{
  private lateinit var addViewModel: AddViewModel
  @Mock
  lateinit var repository: MovieRepository

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

}