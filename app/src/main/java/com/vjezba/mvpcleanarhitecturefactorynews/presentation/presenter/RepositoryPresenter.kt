package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.GithubInteractor
import com.vjezba.domain.usecase.GithubContract
import kotlin.coroutines.CoroutineContext
import com.vjezba.domain.Result
import com.vjezba.domain.entities.RepositoryDetails
import kotlinx.coroutines.*

class RepositoryPresenter(private val githubInteractor: GithubInteractor) : GithubContract.RepositoryPresenter,
    CoroutineScope {

    var page: Int = 1
    var pageNumber: Int = 15

    private var view: GithubContract.RepositoryView? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var job: Job? = null

    override fun attachView(view: GithubContract.RepositoryView) {
        this.view = view
    }

    override fun deattachView(view: GithubContract.RepositoryView?) {
        this.view = view
    }

    override fun getRepositories(repository: String, sort: String, order: String, showOtherData: Boolean) {
        job = launch {
            getRepositoriesAsync(repository, sort, order, showOtherData)
        }
    }

    suspend fun getRepositoriesAsync(repository: String, sort: String, order: String, showOtherData: Boolean) {
        view?.showProgress()

        if( !showOtherData )
            page = 1

        when (val result = githubInteractor.getRepositories(repository, sort, order, page, pageNumber)) {
            is Result.Success -> {
                view?.setRepository(result.data)
                page++
            }
            is Result.Error -> {
                result.throwable.message?.let {
                    view?.showMessage(it)
                }
            }
        }
        view?.hideProgress()
    }

    override fun isNewSearchNewQueryForRepositoriesStarted(showOtherData: Boolean) {
        if( !showOtherData )
            view?.clearAdapterThatHasOldSearchData()
    }

}