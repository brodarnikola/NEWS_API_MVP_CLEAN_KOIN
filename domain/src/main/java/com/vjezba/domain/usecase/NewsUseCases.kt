package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView


interface NewsUseCases {

    // news screen
    interface NewsView : BaseView {
        fun showMessage(message: String)
        fun setNews(articles: List<Articles>)
        fun clearAdapterThatHasOldData()
    }

    interface NewsPresenter: BasePresenter<NewsView>{
        fun getNews(deleteOldAdapterData: Boolean)
        fun stopJobForGettingFreshNews()
    }

}