package com.raywenderlich.wewatch.add

class AddMovieContract {
    interface PresenterInterface {
        fun addMovie(title: String, releaseDate: String, posterPath: String)
    }

    interface ViewInterface {
        fun returnToMain()
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}