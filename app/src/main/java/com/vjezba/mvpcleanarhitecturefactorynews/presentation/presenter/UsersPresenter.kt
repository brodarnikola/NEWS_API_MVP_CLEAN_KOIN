package com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter

import com.vjezba.domain.GithubInteractor
import com.vjezba.domain.usecase.GithubContract

class UsersPresenter(private val githubInteractor: GithubInteractor) : GithubContract.UserPresenter  {

    private var view: GithubContract.UserView? = null

    override fun attachView(view: GithubContract.UserView) {
        this.view = view
    }

    override fun deattachView(view: GithubContract.UserView?) {
        this.view = view
    }

    override fun startToDisplayUserDetailsInBrowser( userHtmlLink: String) {
        view?.showUserDetailInExternalBrowser(userHtmlLink)
    }
}