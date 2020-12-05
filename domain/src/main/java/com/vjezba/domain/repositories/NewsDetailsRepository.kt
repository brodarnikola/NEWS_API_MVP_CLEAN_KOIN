package com.vjezba.domain

import com.vjezba.domain.entities.*


interface NewsDetailsRepository {

    suspend fun getNewsFromRoom(): Result<List<Articles>>
}