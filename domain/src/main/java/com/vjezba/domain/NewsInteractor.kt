package com.vjezba.domain

import com.vjezba.domain.entities.*


interface NewsInteractor {

    suspend fun getRepositories(): Result<List<Articles>>

    suspend fun getNewsFromRoom(): Result<List<Articles>>




    suspend fun getUsers(userRepoString: String): Result<MainResponse>

    suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails>

}