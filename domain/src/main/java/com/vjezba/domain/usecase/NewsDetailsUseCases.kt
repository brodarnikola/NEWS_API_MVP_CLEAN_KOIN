package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView


interface NewsDetailsUseCases {

    // news details screen
    interface NewsDetailsView : BaseView {
        fun displayNewsDetails( newsDetails: List<Articles>)
        fun showMessage(message: String)
    }

    interface NewsDetailsPresenter : BasePresenter<NewsDetailsView> {
        fun loadNewsFromRoom()
    }
}