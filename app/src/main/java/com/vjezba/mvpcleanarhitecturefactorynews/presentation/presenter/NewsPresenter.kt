package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import android.util.Log
import com.vjezba.domain.NewsInteractor
import com.vjezba.domain.usecase.NewsUseCases
import com.vjezba.domain.Result
import kotlinx.coroutines.*

class NewsPresenter(private val newsInteractor: NewsInteractor) :
    NewsUseCases.NewsPresenter {

    private val UPDATE_PERIOD = 500000L

    private var view: NewsUseCases.NewsView? = null

    private var job: Job? = null

    override fun attachView(view: NewsUseCases.NewsView) {
        this.view = view
    }

    override fun deattachView(view: NewsUseCases.NewsView?) {
        this.view = view
    }

    override fun getNews(deleteOldAdapterData: Boolean) {

        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                try {
                    view?.showProgress()
                    withContext(Dispatchers.IO) {
                        Log.d("Da li ce uci sim", "Da li ce uci sim. pocetak dohvacanja novih podataka")
                        getNewsAsync(deleteOldAdapterData)
                    }
                } catch (ex: Exception) {
                    Log.e("Periodic remote failed", ex.toString())
                }
                delay(UPDATE_PERIOD)
            }
        }
    }

    suspend fun getNewsAsync(deleteOldAdapterData: Boolean) {

        when (val result = newsInteractor.getRepositories()) {

            is Result.Success -> {
                withContext(Dispatchers.Main) {
                    if( deleteOldAdapterData && result.data.isNotEmpty() )
                        view?.clearAdapterThatHasOldData()
                    view?.hideProgress()
                    view?.setNews(result.data)
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

    /*override fun isNewSearchNewQueryForRepositoriesStarted(showOtherData: Boolean) {
        if( !showOtherData )
            view?.clearAdapterThatHasOldSearchData()
    }*/

    override fun stopJobForGettingFreshNews() {
        job?.cancel()
    }

}