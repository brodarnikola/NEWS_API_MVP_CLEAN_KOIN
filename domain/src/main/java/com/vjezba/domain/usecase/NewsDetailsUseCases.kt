package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles


interface NewsDetailsUseCases {

    // repository details screen
    interface NewsDetailsView {
        fun displayNewsDetails( newsDetails: List<Articles>)
        fun showMessage(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface NewsDetailsPresenter{
        fun attachView(view: NewsDetailsView)
        fun deattachView(view: NewsDetailsView?)
        fun loadNewsFromRoom()
    }
}