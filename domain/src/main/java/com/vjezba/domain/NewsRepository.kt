package com.vjezba.domain

import com.vjezba.domain.entities.*


interface NewsRepository {

    suspend fun getRepositories(): Result<List<Articles>>

    suspend fun getNewsFromRoom(): Result<List<Articles>>

}