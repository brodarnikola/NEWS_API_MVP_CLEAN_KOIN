package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles


interface NewsUseCases {

    // repositories screen
    interface NewsView {
        fun showProgress()
        fun hideProgress()
        fun showMessage(message: String)
        fun setNews(articles: List<Articles>)
        fun clearAdapterThatHasOldData()
    }

    interface NewsPresenter{
        fun attachView(view: NewsView)
        fun deattachView(view: NewsView?)
        fun getNews(deleteOldAdapterData: Boolean)
        fun stopJobForGettingFreshNews()
    }

}