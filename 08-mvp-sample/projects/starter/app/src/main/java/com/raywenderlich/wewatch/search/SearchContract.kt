package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.model.TmdbResponse

class SearchContract {
    interface PresenterInterface {
        fun getSearchResult(query: String)
        fun stop()
    }

    interface ViewInterface {
        fun displayResult(tmdbResponse: TmdbResponse)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}