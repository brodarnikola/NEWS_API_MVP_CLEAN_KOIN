package com.vjezba.mvpcleanarhitecturefactorynews

import android.app.Application
import android.view.View
import com.vjezba.data.di.dataModule
import com.vjezba.data.di.databaseModule
import com.vjezba.domain.coreModule
import com.vjezba.mvpcleanarhitecturefactorynews.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                presentationModule, coreModule,
                dataModule, databaseModule
            )
        }
    }
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

