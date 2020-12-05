package org.koin.sampleapp.util.mvp

interface BasePresenter<T> {

    fun attachView(view: T)
    fun deattachView(view: T?)
}