package com.vjezba.domain.entities


data class RepositoryOwnerDetails(val login: String, val avatar_url: String = "",  val html_url: String = "", val repos_url: String = "", val followers_url: String = "", val site_admin: Boolean = false)