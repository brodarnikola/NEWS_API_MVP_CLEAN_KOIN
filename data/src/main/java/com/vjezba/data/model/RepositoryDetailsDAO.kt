package com.vjezba.data.model


import com.google.gson.annotations.SerializedName
import com.vjezba.domain.entities.RepositoryDetails
import com.vjezba.domain.entities.RepositoryOwnerDetails
import java.util.*

data class RepositoryDetailsDAO(
    val id: Long = 0,
    @SerializedName("owner")
    val owner: RepositoryOwnerDetails = RepositoryOwnerDetails(""),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("stargazers_count")
    val stargazers_count: String? = "",
    @SerializedName("watchers_count")
    val watchers_count: String? = "",
    val forks: String? = "",
    @SerializedName("open_issues")
    val open_issues: Int? = 0,
    @SerializedName("html_url")
    val html_url: String? = "",

    @SerializedName("language")
    val language: String? = "",
    @SerializedName("created_at")
    val created_at: Date? = Date(),
    @SerializedName("updated_at")
    val updated_at: Date? = Date(),
    @SerializedName("pushed_at")
    val pushed_at: Date? = Date(),
    @SerializedName("has_downloads")
    val has_downloads: Boolean? = false

)


fun RepositoryDetailsDAO.mapToRepositoryDetails(): RepositoryDetails {
    return RepositoryDetails(id, owner, name, description, stargazers_count, watchers_count, forks, open_issues, html_url, language, created_at, updated_at, pushed_at, has_downloads)
}
