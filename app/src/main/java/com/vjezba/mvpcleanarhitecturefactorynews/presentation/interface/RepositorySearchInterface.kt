package com.vjezba.mvpcleanarhitecturefactorynews.presentation.`interface`

interface RepositorySearchInterface {

    fun startSearch(keyword: String, sort: String, order: String, showOtherData: Boolean)

}