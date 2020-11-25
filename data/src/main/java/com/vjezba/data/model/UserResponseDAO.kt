package com.vjezba.data.model


import com.google.gson.annotations.SerializedName
import com.vjezba.domain.entities.UserRepo

data class UserResponseDAO(
    val login: String = "",
    val id: Int = 0,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("followers_url")
    val followersUrl: String = "",
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String = "",
    @SerializedName("repos_url")
    val reposUrl: String = "",
    val type: String = "",
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    val score: Double = 0.0
)


fun UserResponseDAO.mapToUserRepo(): UserRepo {
    return UserRepo(login, id, avatarUrl, followersUrl, subscriptionsUrl, reposUrl, type, siteAdmin, score)
}