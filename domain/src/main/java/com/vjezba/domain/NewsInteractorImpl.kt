package com.vjezba.domain

import com.vjezba.domain.entities.*


class NewsInteractorImpl(private val newsRepository: NewsRepository) : NewsInteractor {

    override suspend fun getRepositories(): Result<List<Articles>> {
        return  newsRepository.getRepositories()
    }
}