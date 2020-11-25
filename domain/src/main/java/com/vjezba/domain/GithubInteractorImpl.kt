package com.vjezba.domain

import com.vjezba.domain.entities.MainResponse
import com.vjezba.domain.entities.Repository
import com.vjezba.domain.entities.RepositoryDetails


class GithubInteractorImpl(private val githubRepository: GithubRepository) : GithubInteractor {

    override suspend fun getRepositories(repository: String, sort: String, order: String, page: Int, pageNumber: Int): Result<Repository> {
        return  githubRepository.getRepositories(repository, sort, order, page, pageNumber)
    }

    override suspend fun getUsers(userRepoString: String): Result<MainResponse> {
        return githubRepository.getUserRepo(userRepoString)
    }

    override suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails> {
        return githubRepository.getRepositoryDetails(repositoryId)
    }
}