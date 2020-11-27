package com.vjezba.data


import android.util.Log
import com.vjezba.data.db.NewsDatabase
import com.vjezba.data.db.entities.DBArticles
import com.vjezba.data.db.mapper.DbMapper
import com.vjezba.data.model.mapToMainReponse
import com.vjezba.data.model.mapToNewsDomain
import com.vjezba.data.model.mapToRepositoryDetails
import com.vjezba.data.service.NewsService
import com.vjezba.domain.NewsRepository
import com.vjezba.domain.entities.MainResponse

import com.vjezba.domain.Result
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.RepositoryDetails
import java.net.UnknownHostException

class NewsRepositoryImpl(private val serviceProvider: NewsService, private val dbNews: NewsDatabase,
                         private val dbMapper: DbMapper) : NewsRepository {

    private var initDatabaseArticlesList: MutableList<DBArticles> = mutableListOf()


    override suspend fun getRepositories(): Result<List<Articles>> {
        return try {
            val mainResponseDAO = serviceProvider.getRepositoryAsync().await()
            val news = mainResponseDAO.mapToNewsDomain()

            /// tu bih mogel gledati ako su mi podaci s servera prazni, ili ih nema,, onda idemo s baze podataka
            // ako idem s baze podataka, onda si ih moram tak mapirati da mi paseju isto kao i podaci od rest api

            // podaci s rest api
            if( news.articles.isNotEmpty() ) {
                dbNews.newsDao().updateNews(dbMapper.mapDomainNewsToDbNews(mainResponseDAO))
                Log.d("Da li ce uci sim", "Da li ce uci sim. uspjesno dohvatili nove podatke s backenda")
                Result.Success(news.articles)
            }
            // podaci s baze podataka
            else {
                val test = dbNews.newsDao().getNews()
                var test2 = dbMapper.mapDBArticlesToArticles(test)
                Log.d("Da li ce uci sim", "Da li ce uci sim. neuspjesnoo, nismo uspjeli dohvatiti nove podatke s backenda")
                Result.Success(test2)
            }


            // mogao bih si napraviti singleton uz pomoc:
            // 1) object classe i provjeravati dal je proslo 5 minuta
            // 2) uz pomoc singleton classe iz koin-a nekak


        }
        catch (e: UnknownHostException) {
            if( !initDatabaseArticlesList.containsAll(dbNews.newsDao().getNews())  ) {
                val listDbArticles = dbNews.newsDao().getNews()
                val listArticles = dbMapper.mapDBArticlesToArticles(listDbArticles)
                initDatabaseArticlesList.addAll(listDbArticles)
                Log.d("Da li ce uci sim", "Da li ce uci sim. prikazujemo stare podatke od room, od nase baze podatakaa")
                Result.Success(listArticles)
            }
            else {
                Result.Success(listOf())
            }
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