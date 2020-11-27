package com.vjezba.domain

import com.vjezba.domain.entities.*


class NewsInteractorImpl(private val newsRepository: NewsRepository) : NewsInteractor {

    override suspend fun getRepositories(): Result<List<Articles>> {
        return  newsRepository.getRepositories()
    }

    override suspend fun getNewsFromRoom(): Result<List<Articles>> {
        return newsRepository.getNewsFromRoom()
    }




    override suspend fun getUsers(userRepoString: String): Result<MainResponse> {
        return newsRepository.getUserRepo(userRepoString)
    }

    override suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails> {
        return newsRepository.getRepositoryDetails(repositoryId)
    }
}