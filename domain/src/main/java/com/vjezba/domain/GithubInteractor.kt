package com.vjezba.domain

import com.vjezba.domain.entities.MainResponse
import com.vjezba.domain.entities.News
import com.vjezba.domain.entities.Repository
import com.vjezba.domain.entities.RepositoryDetails


interface GithubInteractor {

    suspend fun getRepositories(repository: String, sort: String, order: String, page: Int, numberOfItems: Int): Result<News>

    suspend fun getUsers(userRepoString: String): Result<MainResponse>

    suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails>

}