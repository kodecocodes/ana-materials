package com.raywenderlich.wewatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.wewatch.data.MovieRepository
import com.raywenderlich.wewatch.data.MovieRepositoryImpl
import com.raywenderlich.wewatch.data.model.Movie

class SearchViewModel(private val repository: MovieRepository = MovieRepositoryImpl()): ViewModel()  {

  fun searchMovie(query: String): LiveData<List<Movie>?> {
    return repository.searchMovies(query)
  }

  fun saveMovie(movie: Movie) {
    repository.saveMovie(movie)
  }
}