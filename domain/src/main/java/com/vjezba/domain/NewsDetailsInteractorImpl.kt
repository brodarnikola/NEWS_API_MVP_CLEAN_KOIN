package com.vjezba.domain

import com.vjezba.domain.entities.*


class NewsDetailsInteractorImpl(private val newsDetailsInteractor: NewsDetailsRepository) : NewsDetailsInteractor {

    override suspend fun getNewsFromRoom(): Result<List<Articles>> {
        return newsDetailsInteractor.getNewsFromRoom()
    }

}