package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.NewsInteractor
import com.vjezba.domain.usecase.NewsContract

class UsersPresenter(private val newsInteractor: NewsInteractor) : NewsContract.UserPresenter  {

    private var view: NewsContract.UserView? = null

    override fun attachView(view: NewsContract.UserView) {
        this.view = view
    }

    override fun deattachView(view: NewsContract.UserView?) {
        this.view = view
    }

    override fun startToDisplayUserDetailsInBrowser( userHtmlLink: String) {
        view?.showUserDetailInExternalBrowser(userHtmlLink)
    }
}