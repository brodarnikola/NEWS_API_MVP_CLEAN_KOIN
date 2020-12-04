package com.vjezba.domain

import com.vjezba.domain.entities.*


interface NewsDetailsInteractor {

    suspend fun getNewsFromRoom(): Result<List<Articles>>
}