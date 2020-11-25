package com.vjezba.data.di

import com.vjezba.data.GithubRepositoryImpl
import com.vjezba.data.db.NewsDatabase
import com.vjezba.data.db.mapper.DbMapper
import com.vjezba.data.db.mapper.DbMapperImpl
import com.vjezba.domain.GithubRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


//it creates a module in Koin which would be used by Koin to provide all the dependencies.
val databaseModule = module {

    //it creates a singleton that can be used across the app as a singular instance.
    single { NewsDatabase.create(androidContext()) }

    single { get<NewsDatabase>().newsDao() }

    factory { val dbMapper: DbMapper =
        DbMapperImpl()
        dbMapper }

}