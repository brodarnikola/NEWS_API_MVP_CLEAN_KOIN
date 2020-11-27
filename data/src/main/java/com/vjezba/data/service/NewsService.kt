package com.vjezba.data.service

import com.vjezba.data.model.ApiNews
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsService {

    @GET("articles?source=bbc-news&sortBy=top&apiKey=b0124155d6224507b85f340dcbf20df4")
    @Headers("Content-Type: application/json")
    fun getRepositoryAsync(): Deferred<ApiNews>

}