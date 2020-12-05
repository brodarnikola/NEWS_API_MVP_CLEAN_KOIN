package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.NewsDetailsRepository
import com.vjezba.domain.Result
import com.vjezba.domain.usecase.NewsDetailsUseCases
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsDetailsPresenter(private val newsDetailsRepository: NewsDetailsRepository) :
    NewsDetailsUseCases.NewsDetailsPresenter, CoroutineScope {

    private var view: NewsDetailsUseCases.NewsDetailsView? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private var job: Job? = null

    override fun attachView(view: NewsDetailsUseCases.NewsDetailsView) {
        this.view = view
    }

    override fun deattachView(view: NewsDetailsUseCases.NewsDetailsView?) {
        this.view = view
    }

    override fun loadNewsFromRoom() {
        job?.cancel()
        job = launch {
            val result = newsDetailsRepository.getNewsFromRoom()
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

}