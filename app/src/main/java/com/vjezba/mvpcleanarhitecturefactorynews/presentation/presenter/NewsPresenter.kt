package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import android.util.Log
import com.vjezba.domain.NewsInteractor
import com.vjezba.domain.usecase.NewsContract
import com.vjezba.domain.Result
import kotlinx.coroutines.*

class NewsPresenter(private val newsInteractor: NewsInteractor) :
    NewsContract.NewsPresenter {

    var page: Int = 1
    var pageNumber: Int = 15

    private val UPDATE_PERIOD = 500000L

    private var view: NewsContract.NewsView? = null

    private var job: Job? = null

    override fun attachView(view: NewsContract.NewsView) {
        this.view = view
    }

    override fun deattachView(view: NewsContract.NewsView?) {
        this.view = view
    }

    override fun getNews(showOtherData: Boolean) {

        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                try {
                    view?.showProgress()
                    withContext(Dispatchers.IO) {
                        Log.d("Da li ce uci sim", "Da li ce uci sim. pocetak dohvacanja novih podataka")
                        getRepositoriesAsync(showOtherData)
                    }
                } catch (ex: Exception) {
                    Log.e("Periodic remote failed", ex.toString())
                }
                delay(UPDATE_PERIOD)
            }
        }

//        job = launch(Dispatchers.IO) {
//            getRepositoriesAsync(repository, sort, order, showOtherData)
//        }
    }

    suspend fun getRepositoriesAsync(showOtherData: Boolean) {

        if( !showOtherData )
            page = 1

        when (val result = newsInteractor.getRepositories()) {

            is Result.Success -> {
                withContext(Dispatchers.Main) {
                    view?.hideProgress()
                    view?.setNews(result.data)
                    page++
                }
            }
            is Result.Error -> {
                withContext(Dispatchers.Main) {
                    result.throwable.message?.let {
                        view?.hideProgress()
                        view?.showMessage(it)
                    }
                }
            }
        }
    }

    override fun isNewSearchNewQueryForRepositoriesStarted(showOtherData: Boolean) {
        if( !showOtherData )
            view?.clearAdapterThatHasOldSearchData()
    }

    override fun stopJobForGettingFreshNews() {
        job?.cancel()
    }

}