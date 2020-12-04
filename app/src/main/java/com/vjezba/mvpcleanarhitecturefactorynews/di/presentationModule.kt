package com.vjezba.mvpcleanarhitecturefactorynews.di

import com.vjezba.domain.usecase.NewsDetailsUseCases
import com.vjezba.domain.usecase.NewsUseCases
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.NewsDetailsPresenter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.presenter.NewsPresenter
import org.koin.dsl.module

//it creates a module in Koin which would be used by Koin to provide all the dependencies.
val presentationModule = module {

    // it provides a bean definition, which will create a new instance each time it is injected.
    factory {
        val newsPresenter: NewsUseCases.NewsPresenter =
            NewsPresenter(
                //it is used in the constructor of a class to provide the required dependency.
                get()
            )
        newsPresenter
    }

    factory {
        val newsDetailsPresenter: NewsDetailsUseCases.NewsDetailsPresenter =
            NewsDetailsPresenter(
                get()
            )
        newsDetailsPresenter
    }
}