package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import android.util.Log
import com.vjezba.domain.GithubInteractor
import com.vjezba.domain.usecase.GithubContract
import kotlin.coroutines.CoroutineContext
import com.vjezba.domain.Result
import kotlinx.coroutines.*

class RepositoryPresenter(private val githubInteractor: GithubInteractor) : GithubContract.RepositoryPresenter {

    var page: Int = 1
    var pageNumber: Int = 15

    private val UPDATE_PERIOD = 10000L

    private var view: GithubContract.RepositoryView? = null

    private var job: Job? = null

    override fun attachView(view: GithubContract.RepositoryView) {
        this.view = view
    }

    override fun deattachView(view: GithubContract.RepositoryView?) {
        this.view = view
    }

    override fun getRepositories(repository: String, sort: String, order: String, showOtherData: Boolean) {

        job?.cancel()
        job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                try {
                    view?.showProgress()
                    withContext(Dispatchers.IO) {
                        Log.d("Da li ce uci sim", "Da li ce uci sim")
                        getRepositoriesAsync(repository, sort, order, showOtherData)
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

    suspend fun getRepositoriesAsync(repository: String, sort: String, order: String, showOtherData: Boolean) {

        if( !showOtherData )
            page = 1

        when (val result = githubInteractor.getRepositories(repository, sort, order, page, pageNumber)) {

            is Result.Success -> {
                withContext(Dispatchers.Main) {
                    view?.hideProgress()
                    view?.setRepository(result.data)
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