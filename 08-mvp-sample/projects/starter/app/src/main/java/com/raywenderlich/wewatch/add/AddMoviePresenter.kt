package com.raywenderlich.wewatch.add

import com.raywenderlich.wewatch.model.LocalDataSource

class AddMoviePresenter(
    private var viewInterface: AddMovieContract.ViewInterface,
    private var dataSource: LocalDataSource
) : AddMovieContract.PresenterInterface {

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {

    }
}