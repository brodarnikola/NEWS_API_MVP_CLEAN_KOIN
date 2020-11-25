package com.vjezba.domain.entities


data class UserRepo(val login: String, val id: Int, val avatar_url: String, val followers_url: String, val subscriptions_url: String,
                    val repos_url: String, val type: String, val site_admin: Boolean, val score: Double)