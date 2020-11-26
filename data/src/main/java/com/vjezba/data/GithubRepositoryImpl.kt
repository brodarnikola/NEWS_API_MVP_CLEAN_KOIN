package com.vjezba.data


import com.vjezba.data.db.NewsDatabase
import com.vjezba.data.db.mapper.DbMapper
import com.vjezba.data.model.mapToMainReponse
import com.vjezba.data.model.mapToNewsDomain
import com.vjezba.data.model.mapToRepositoryDetails
import com.vjezba.data.service.NewsService
import com.vjezba.domain.GithubRepository
import com.vjezba.domain.entities.MainResponse

import com.vjezba.domain.Result
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.News
import com.vjezba.domain.entities.RepositoryDetails
import kotlinx.coroutines.flow.toList
import java.net.UnknownHostException

class GithubRepositoryImpl(private val serviceProvider: NewsService, private val dbNews: NewsDatabase,
                           private val dbMapper: DbMapper) : GithubRepository {

    override suspend fun getRepositories(repository: String, sort: String, order: String, page: Int, pageNumber: Int): Result<List<Articles>> {
        return try {
            val mainResponseDAO = serviceProvider.getRepositoryAsync(repository, sort, order, page, pageNumber).await()
            val news = mainResponseDAO.mapToNewsDomain()
            dbNews.newsDao().updateNews(dbMapper.mapDomainNewsToDbNews(mainResponseDAO))

            /// tu bih mogel gledati ako su mi podaci s servera prazni, ili ih nema,, onda idemo s baze podataka
            // ako idem s baze podataka, onda si ih moram tak mapirati da mi paseju isto kao i podaci od rest api

            // podaci s rest api
            if( news.articles.isNotEmpty() ) {
                Result.Success(news.articles)
            }
            // podaci s baze podataka
            else {
                val test = dbNews.newsDao().getNews()
                var test2 = dbMapper.mapDBArticlesToArticles(test)
                Result.Success(test2)
            }


            // mogao bih si napraviti singleton uz pomoc:
            // 1) object classe i provjeravati dal je proslo 5 minuta
            // 2) uz pomoc singleton classe iz koin-a nekak


        }
        catch (e: UnknownHostException) {
            val test = dbNews.newsDao().getNews()
            var test2 = dbMapper.mapDBArticlesToArticles(test)
            Result.Success(test2)
        }
        catch (e: Throwable) {
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