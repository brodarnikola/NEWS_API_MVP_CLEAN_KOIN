package com.vjezba.data


import android.util.Log
import com.vjezba.data.db.NewsDatabase
import com.vjezba.data.db.entities.DBArticles
import com.vjezba.data.db.mapper.DbMapper
import com.vjezba.data.model.mapToNewsDomain
import com.vjezba.data.service.NewsService
import com.vjezba.domain.NewsRepository

import com.vjezba.domain.Result
import com.vjezba.domain.entities.Articles
import java.net.UnknownHostException

class NewsRepositoryImpl(private val serviceProvider: NewsService, private val dbNews: NewsDatabase,
                         private val dbMapper: DbMapper) : NewsRepository {

    private var initDatabaseArticlesList: MutableList<DBArticles> = mutableListOf()


    override suspend fun getRepositories(): Result<List<Articles>> {
        return try {
            val mainResponseDAO = serviceProvider.getRepositoryAsync().await()
            val news = mainResponseDAO.mapToNewsDomain()

            // data from rest api
            if( news != null && news.articles.isNotEmpty() ) {
                dbNews.newsDao().updateNews(dbMapper.mapDomainNewsToDbNews(mainResponseDAO))
                Log.d("Da li ce uci sim", "Da li ce uci sim. uspjesno dohvatili nove podatke s rest api")
                Result.Success(news.articles)
            }
            // data from room
            else {
                val listDbArticles = dbNews.newsDao().getNews()
                var listArticles = dbMapper.mapDBArticlesToArticles(listDbArticles)
                Log.d("Da li ce uci sim", "Da li ce uci sim. neuspjesnoo, nismo uspjeli dohvatiti nove podatke s backenda")
                Result.Success(listArticles)
            }
        }
        catch (e: UnknownHostException) {
            // if user does not have network connection, then display old data from room, but only once
            if( !initDatabaseArticlesList.containsAll(dbNews.newsDao().getNews())  ) {
                val listDbArticles = dbNews.newsDao().getNews()
                val listArticles = dbMapper.mapDBArticlesToArticles(listDbArticles)
                initDatabaseArticlesList.addAll(listDbArticles)
                Log.d("Da li ce uci sim", "Da li ce uci sim. prikazujemo stare podatke od room, od nase baze podatakaa")
                Result.Success(listArticles)
            }
            else {
                Log.d("Da li ce uci sim", "Da li ce uci sim. saljemo prazno listu")
                Result.Success(listOf())
            }
        }
        catch (e: Throwable) {
            if( !initDatabaseArticlesList.containsAll(dbNews.newsDao().getNews())  ) {
                val listDbArticles = dbNews.newsDao().getNews()
                val listArticles = dbMapper.mapDBArticlesToArticles(listDbArticles)
                initDatabaseArticlesList.addAll(listDbArticles)
                Log.d("Da li ce uci sim", "Da li ce uci sim. prikazujemo stare podatke od room, od nase baze podatakaa")
                Result.Success(listArticles)
            }
            else {
                Result.Error(e)
            }
        }
    }

    override suspend fun getNewsFromRoom(): Result<List<Articles>> {
        val listDbArticles = dbNews.newsDao().getNews()
        var listArticles = dbMapper.mapDBArticlesToArticles(listDbArticles)
        Log.d("Da li ce uci sim", "Da li ce uci sim. neuspjesnoo, nismo uspjeli dohvatiti nove podatke s backenda")
        return Result.Success(listArticles)
    }


}