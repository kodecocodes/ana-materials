package com.raywenderlich.wewatch.main

import com.raywenderlich.wewatch.model.LocalDataSource

class MainPresenter(
    private var viewInterface: MainContract.ViewInterface,
    private var localDataSource: LocalDataSource): MainContract.PresenterInterface {

    private val TAG = "MainPresenter"
}