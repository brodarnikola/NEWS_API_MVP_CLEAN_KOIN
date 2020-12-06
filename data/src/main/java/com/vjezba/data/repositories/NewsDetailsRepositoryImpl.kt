package com.vjezba.data.repositories


import android.util.Log
import com.vjezba.data.db.NewsDatabase
import com.vjezba.data.db.mapper.DbMapper
import com.vjezba.domain.NewsDetailsRepository
import com.vjezba.domain.Result
import com.vjezba.domain.entities.Articles

class NewsDetailsRepositoryImpl(private val dbNews: NewsDatabase,
                                private val dbMapper: DbMapper) : NewsDetailsRepository {

    override suspend fun getNewsFromRoom(): Result<List<Articles>> {
        val listDbArticles = dbNews.newsDao().getNews()
        val listArticles = dbMapper.mapDBArticlesToArticles(listDbArticles)
        Log.d("Da li ce uci sim", "Da li ce uci sim. neuspjesnoo, nismo uspjeli dohvatiti nove podatke s backenda")
        return Result.Success(listArticles)
    }

}