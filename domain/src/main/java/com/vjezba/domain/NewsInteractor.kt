package com.vjezba.domain

import com.vjezba.domain.entities.*


interface NewsInteractor {

    suspend fun getRepositories(): Result<List<Articles>>
}