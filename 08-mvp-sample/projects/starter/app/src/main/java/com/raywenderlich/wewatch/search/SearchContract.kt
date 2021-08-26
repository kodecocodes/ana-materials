package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.model.Movie

class SearchContract {
    interface PresenterInterface {
        fun getSearchResult(query: String)
        fun stop()
    }

    interface ViewInterface {
        fun displayResult(movies: List<Movie>)
        fun displayMessage(message: String)
        fun displayError(message: String)
        fun displayNoMovieFound()
    }
}