package com.vjezba.domain.usecase

import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.News
import com.vjezba.domain.entities.Repository
import com.vjezba.domain.entities.RepositoryDetails


interface GithubContract {

    // repositories screen
    interface RepositoryView {
        fun showProgress()
        fun hideProgress()
        fun showMessage(message: String)
        fun setRepository(repository: List<Articles>)
        fun clearAdapterThatHasOldSearchData()
    }

    interface RepositoryPresenter{
        fun attachView(view: RepositoryView)
        fun deattachView(view: RepositoryView?)
        fun getRepositories(repository: String, sort: String, order: String, showOtherData: Boolean)
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
        fun showMessage(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface RepositoryDetailsPresenter{
        fun attachView(view: RepositoryDetailsView)
        fun deattachView(view: RepositoryDetailsView?)
        fun loadRepositoryDetailsById( repositoryId: Long)
    }
}