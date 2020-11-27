package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.NewsInteractor
import com.vjezba.domain.Result
import com.vjezba.domain.usecase.NewsContract
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsDetailsPresenter(private val newsInteractor: NewsInteractor) :
    NewsContract.NewsDetailsPresenter, CoroutineScope {

    private var view: NewsContract.NewsDetailsView? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private var job: Job? = null

    override fun attachView(view: NewsContract.NewsDetailsView) {
        this.view = view
    }

    override fun deattachView(view: NewsContract.NewsDetailsView?) {
        this.view = view
    }

    override fun loadNewsFromRoom() {
        job?.cancel()
        job = launch {
            val result = newsInteractor.getNewsFromRoom()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        view?.hideProgress()
                        view?.displayNewsDetails(result.data)

                    }
                    is Result.Error -> {
                        result.throwable.message?.let {
                            view?.hideProgress()
                            view?.showMessage(it)
                        }
                    }
                }
            }
        }
    }

    /*override  fun loadRepositoryDetailsById(repositoryId: Long) {
        job = launch {
            getRepositoryDetails(repositoryId)
        }
    }

    private suspend fun getRepositoryDetails(repositoryId: Long) {
        view?.showProgress()
        when (val result = newsInteractor.getRepositoryDetails(repositoryId)) {
            is Result.Success -> {
                view?.displayRepositoryDetails(result.data)
            }
            is Result.Error ->
                result.throwable.message?.let {
                    view?.showMessage(it)
                }
        }
        view?.hideProgress()
    }*/

}