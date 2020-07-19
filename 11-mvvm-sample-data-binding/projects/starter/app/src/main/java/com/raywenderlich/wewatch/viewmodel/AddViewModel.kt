package com.raywenderlich.wewatch.viewmodel

import androidx.lifecycle.ViewModel
import com.raywenderlich.wewatch.data.MovieRepository
import com.raywenderlich.wewatch.data.MovieRepositoryImpl
import com.raywenderlich.wewatch.data.model.Movie

class AddViewModel(private val repository: MovieRepository = MovieRepositoryImpl()): ViewModel()  {

  fun saveMovie(movie: Movie) {
    repository.saveMovie(movie)
  }
}