package com.vjezba.domain

import com.vjezba.domain.entities.*


interface GithubRepository {

    suspend fun getRepositories(repository: String, sort: String, order: String, page: Int, pageNumber: Int): Result<List<Articles>>

    suspend fun getUserRepo(userRepoString: String): Result<MainResponse>

    suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails>
}