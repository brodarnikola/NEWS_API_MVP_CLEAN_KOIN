package com.vjezba.domain

import com.vjezba.domain.entities.*


interface GithubInteractor {

    suspend fun getRepositories(repository: String, sort: String, order: String, page: Int, numberOfItems: Int): Result<List<Articles>>

    suspend fun getUsers(userRepoString: String): Result<MainResponse>

    suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails>

}