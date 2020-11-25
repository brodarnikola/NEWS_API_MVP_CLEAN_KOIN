package com.vjezba.domain.entities


data class Repository(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepositoryDetails>
)
