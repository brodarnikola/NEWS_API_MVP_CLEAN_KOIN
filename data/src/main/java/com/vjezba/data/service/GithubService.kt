package com.vjezba.data.service

import com.vjezba.data.model.MainResponseDAO
import com.vjezba.data.model.News
import com.vjezba.data.model.RepositoryDetailsDAO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("articles?source=bbc-news&sortBy=top&apiKey=b0124155d6224507b85f340dcbf20df4")
    @Headers("Content-Type: application/json")
    fun getRepositoryAsync(@Query("q") q: String, @Query("sort") sort: String, @Query("order") order: String,
                           @Query("page") page:Int, @Query("per_page") pageNumber: Int): Deferred<News>

    @GET("search/users")
    @Headers("Content-Type: application/json")
    fun getUserRepoAsync(@Query("q") q: String, @Query("page") page: Int, @Query("per_page") perPage: Int): Deferred<MainResponseDAO>


    @GET("repositories/{repositoryId}")
    @Headers("Content-Type: application/json")
    fun getRepositoryDetailsAsync( @Path("repositoryId") repositoryId: Long ): Deferred<RepositoryDetailsDAO>

}