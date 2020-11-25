package com.vjezba.data.model


import com.google.gson.annotations.SerializedName
import com.vjezba.domain.entities.Repository
import com.vjezba.domain.entities.RepositoryDetails

data class News(

    @SerializedName("status")
    val status: String = "",
    @SerializedName("source")
    val source: String = "",
    @SerializedName("sortBy")
    val sortBy: String = "",
    val articles: List<RepositoryDetails>,

    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    val items: List<RepositoryDetails>
)


fun News.mapToRepository(): Repository {
    return Repository(total_count, incomplete_results, items)
}