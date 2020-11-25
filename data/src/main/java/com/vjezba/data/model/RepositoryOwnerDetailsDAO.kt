package com.vjezba.data.model


import com.google.gson.annotations.SerializedName
import com.vjezba.domain.entities.RepositoryOwnerDetails

data class RepositoryOwnerDetailsDAO(
    @SerializedName("login")
    val login: String = "",
    @SerializedName("avatar_url")
    val avatar_url: String = "",
    @SerializedName("html_url")
    val html_url: String = "",
    @SerializedName("repos_url")
    val repos_url: String = "",
    @SerializedName("followers_url")
    val followers_url: String = "",
    @SerializedName("site_admin")
    val site_admin: Boolean = false
)

fun RepositoryOwnerDetailsDAO.mapToRepositoryOwnerDetails(): RepositoryOwnerDetails {
    return RepositoryOwnerDetails(login, avatar_url, html_url, repos_url, followers_url, site_admin)
}
