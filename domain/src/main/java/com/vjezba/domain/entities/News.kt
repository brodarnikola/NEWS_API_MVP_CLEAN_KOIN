package com.vjezba.domain.entities


data class News(

    val status: String = "",
    val source: String = "",
    val sortBy: String = "",
    val articles: List<Articles>

)
