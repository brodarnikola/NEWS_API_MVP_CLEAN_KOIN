package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles


interface NewsContract {

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
        //fun isNewSearchNewQueryForRepositoriesStarted(showOtherData: Boolean)
        fun stopJobForGettingFreshNews()
    }


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