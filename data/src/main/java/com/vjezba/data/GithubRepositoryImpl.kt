package com.vjezba.data


import com.vjezba.data.model.mapToMainReponse
import com.vjezba.data.model.mapToRepository
import com.vjezba.data.model.mapToRepositoryDetails
import com.vjezba.data.service.GithubService
import com.vjezba.domain.GithubRepository
import com.vjezba.domain.entities.MainResponse
import com.vjezba.domain.entities.Repository

import com.vjezba.domain.Result
import com.vjezba.domain.entities.RepositoryDetails

class GithubRepositoryImpl(private val serviceProvider: GithubService) : GithubRepository {

    override suspend fun getRepositories(repository: String, sort: String, order: String, page: Int, pageNumber: Int): Result<Repository> {
        return try {
            val mainResponseDAO = serviceProvider.getRepositoryAsync(repository, sort, order, page, pageNumber).await()
            val repository = mainResponseDAO.mapToRepository()
            Result.Success(repository)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getUserRepo(users: String): Result<MainResponse> {
        return try {
            val mainResponseDAO = serviceProvider.getUserRepoAsync(users, 0, 100).await()
            val users = mainResponseDAO.mapToMainReponse()
            Result.Success(users)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getRepositoryDetails(repositoryId: Long): Result<RepositoryDetails> {
        return try {
            val repositoryDetailsDAO = serviceProvider.getRepositoryDetailsAsync(repositoryId).await()
            val users = repositoryDetailsDAO.mapToRepositoryDetails()//.map { it.mapToMainReponse() }
            Result.Success(users)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }


}