package com.vjezba.mvpcleanarhitecturefactorynews.di

import com.vjezba.domain.usecase.GithubContract
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.RepositoryDetailsPresenter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.RepositoryPresenter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.UsersPresenter
import org.koin.dsl.module

//it creates a module in Koin which would be used by Koin to provide all the dependencies.
val presentationModule = module {

    // it provides a bean definition, which will create a new instance each time it is injected.
    factory {
        val repositoryPresenter: GithubContract.RepositoryPresenter =
            RepositoryPresenter(
                //it is used in the constructor of a class to provide the required dependency.
                get()
            )
        repositoryPresenter
    }

    factory {
        val userDetailsPresenter: GithubContract.UserPresenter =
            UsersPresenter(
                get()
            )
        userDetailsPresenter
    }

    factory {
        val repositoryDetailsPresenter: GithubContract.RepositoryDetailsPresenter =
            RepositoryDetailsPresenter(
                get()
            )
        repositoryDetailsPresenter
    }
}