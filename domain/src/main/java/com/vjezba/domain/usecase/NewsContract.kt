package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.RepositoryDetails


interface NewsContract {

    // repositories screen
    interface NewsView {
        fun showProgress()
        fun hideProgress()
        fun showMessage(message: String)
        fun setNews(repository: List<Articles>)
        fun clearAdapterThatHasOldSearchData()
    }

    interface NewsPresenter{
        fun attachView(view: NewsView)
        fun deattachView(view: NewsView?)
        fun getNews(showOtherData: Boolean)
        fun isNewSearchNewQueryForRepositoriesStarted(showOtherData: Boolean)
        fun stopJobForGettingFreshNews()
    }


    // user details screen
    interface UserView {
        fun showUserDetailInExternalBrowser( userHtmlLink: String)
    }

    interface UserPresenter{
        fun attachView(view: UserView)
        fun deattachView(view: UserView?)
        fun startToDisplayUserDetailsInBrowser( userHtmlLink: String)
    }


    // repository details screen
    interface RepositoryDetailsView {
        fun displayRepositoryDetails( repositoryDetails: RepositoryDetails)

        fun displayNewsDetails( newsDetails: List<Articles>)
        fun showMessage(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface RepositoryDetailsPresenter{
        fun attachView(view: RepositoryDetailsView)
        fun deattachView(view: RepositoryDetailsView?)
        fun loadNewsFromRoom()

        fun loadRepositoryDetailsById( repositoryId: Long)
    }
}