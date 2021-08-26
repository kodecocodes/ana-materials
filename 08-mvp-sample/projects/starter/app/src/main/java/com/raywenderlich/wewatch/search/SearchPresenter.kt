package com.raywenderlich.wewatch.search

import android.util.Log
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource
) : SearchContract.PresenterInterface {

    private val TAG = "SearchPresenter"

    private val compositeDisposable = CompositeDisposable()

    val searchResultObservable: (String) -> Observable<TmdbResponse> = { query ->
        dataSource.searchResultsObservable(query)
    }

    val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {

            override fun onNext(@NonNull tmdbResponse: TmdbResponse) {
                Log.d(TAG, "OnNext ${tmdbResponse.totalResults}")
                viewInterface.displayResult(tmdbResponse)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error fetching movie data")
                viewInterface.displayError("Error fetching movie data.")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    override fun getSearchResult(query: String) {
        val searchResultDisposable = searchResultObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(searchResultDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}