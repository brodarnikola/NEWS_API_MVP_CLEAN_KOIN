package com.vjezba.mvpcleanarhitecturefactorynews.di

import com.vjezba.domain.usecase.NewsContract
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.RepositoryDetailsPresenter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.NewsPresenter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.UsersPresenter
import org.koin.dsl.module

//it creates a module in Koin which would be used by Koin to provide all the dependencies.
val presentationModule = module {

    // it provides a bean definition, which will create a new instance each time it is injected.
    factory {
        val newsPresenter: NewsContract.NewsPresenter =
            NewsPresenter(
                //it is used in the constructor of a class to provide the required dependency.
                get()
            )
        newsPresenter
    }

    factory {
        val userDetailsPresenter: NewsContract.UserPresenter =
            UsersPresenter(
                get()
            )
        userDetailsPresenter
    }

    factory {
        val repositoryDetailsPresenter: NewsContract.RepositoryDetailsPresenter =
            RepositoryDetailsPresenter(
                get()
            )
        repositoryDetailsPresenter
    }
}