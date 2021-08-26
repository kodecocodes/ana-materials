package com.raywenderlich.wewatch.main

import com.raywenderlich.wewatch.model.LocalDataSource

class MainPresenter(private var view: MainActivity, private var localDataSource: LocalDataSource) {
    private val TAG = "MainPresenter"
}