package com.vjezba.data.model


import com.google.gson.annotations.SerializedName
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.News
import com.vjezba.domain.entities.RepositoryDetails

data class ApiNews(

    val status: String = "",
    val source: String = "",
    val sortBy: String = "",
    val articles: List<Articles>,

    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    val items: List<RepositoryDetails>
)


fun ApiNews.mapToNewsDomain(): News {
    return News(status, source, sortBy, articles)
}