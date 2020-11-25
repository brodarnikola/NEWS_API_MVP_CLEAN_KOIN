package com.vjezba.domain.entities

import java.util.*


data class RepositoryDetails(val id: Long, val owner: RepositoryOwnerDetails = RepositoryOwnerDetails(""),
                             val name: String?, val description: String?, val stargazers_count: String?,
                             val watchers_count: String?, val forks: String?, val open_issues: Int?,
                             val html_url: String?, val language: String?, val created_at: Date?,
                             val updated_at: Date?, val pushed_at: Date?, val has_downloads: Boolean?
)