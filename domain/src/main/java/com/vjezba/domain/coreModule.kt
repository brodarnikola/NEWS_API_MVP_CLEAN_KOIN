package com.vjezba.domain

import org.koin.dsl.module

// it creates a module in Koin which would be used by Koin to provide all the dependencies.
val coreModule = module {

    // it provides a bean definition, which will create a new instance each time it is injected.
    factory {
        val githubInteractor: GithubInteractor =
            GithubInteractorImpl(get())
        githubInteractor
    }
}