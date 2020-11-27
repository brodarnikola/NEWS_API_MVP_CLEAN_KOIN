package com.vjezba.domain

import com.vjezba.domain.entities.*


interface NewsRepository {

    suspend fun getRepositories(): Result<List<Articles>>

    suspend fun getNewsFromRoom(): Result<List<Articles>>





    suspend fun getUserRepo(userRepoString: String): Result<MainResponse>

    suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails>
}