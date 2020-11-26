package com.vjezba.data.service

import com.vjezba.data.model.ApiNews
import com.vjezba.data.model.MainResponseDAO
import com.vjezba.data.model.RepositoryDetailsDAO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {

    @GET("articles?source=bbc-news&sortBy=top&apiKey=6e005ad0a1134ed89108da1fe290a141")
    @Headers("Content-Type: application/json")
    fun getRepositoryAsync(@Query("q") q: String, @Query("sort") sort: String, @Query("order") order: String,
                           @Query("page") page:Int, @Query("per_page") pageNumber: Int): Deferred<ApiNews>

    @GET("search/users")
    @Headers("Content-Type: application/json")
    fun getUserRepoAsync(@Query("q") q: String, @Query("page") page: Int, @Query("per_page") perPage: Int): Deferred<MainResponseDAO>


    @GET("repositories/{repositoryId}")
    @Headers("Content-Type: application/json")
    fun getRepositoryDetailsAsync( @Path("repositoryId") repositoryId: Long ): Deferred<RepositoryDetailsDAO>

}