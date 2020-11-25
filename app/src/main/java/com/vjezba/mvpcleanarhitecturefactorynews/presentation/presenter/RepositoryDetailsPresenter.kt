package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.GithubInteractor
import com.vjezba.domain.Result
import com.vjezba.domain.usecase.GithubContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RepositoryDetailsPresenter(private val githubInteractor: GithubInteractor) : GithubContract.RepositoryDetailsPresenter, CoroutineScope {

    private var view: GithubContract.RepositoryDetailsView? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var job: Job? = null

    override fun attachView(view: GithubContract.RepositoryDetailsView) {
        this.view = view
    }

    override fun deattachView(view: GithubContract.RepositoryDetailsView?) {
        this.view = view
    }

    override  fun loadRepositoryDetailsById(repositoryId: Long) {
        job = launch {
            getRepositoryDetails(repositoryId)
        }
    }

    private suspend fun getRepositoryDetails(repositoryId: Long) {
        view?.showProgress()
        when (val result = githubInteractor.getRepositoryDetails(repositoryId)) {
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