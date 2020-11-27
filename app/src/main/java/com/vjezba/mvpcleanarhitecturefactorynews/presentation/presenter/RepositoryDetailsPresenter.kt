package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.NewsInteractor
import com.vjezba.domain.Result
import com.vjezba.domain.usecase.NewsContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RepositoryDetailsPresenter(private val newsInteractor: NewsInteractor) : NewsContract.RepositoryDetailsPresenter, CoroutineScope {

    private var view: NewsContract.RepositoryDetailsView? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var job: Job? = null

    override fun attachView(view: NewsContract.RepositoryDetailsView) {
        this.view = view
    }

    override fun deattachView(view: NewsContract.RepositoryDetailsView?) {
        this.view = view
    }

    override  fun loadRepositoryDetailsById(repositoryId: Long) {
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
    }

}