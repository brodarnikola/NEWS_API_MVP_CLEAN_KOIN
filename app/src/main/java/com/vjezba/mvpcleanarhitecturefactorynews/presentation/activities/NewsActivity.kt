package com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.RepositoryOwnerDetails
import com.vjezba.domain.usecase.NewsContract
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters.NewsAdapter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.dialog.DisableUserActionsDialog
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.hide
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.show
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.ext.android.inject

class NewsActivity : AppCompatActivity(), NewsContract.NewsView {

    private val newsPresenter: NewsContract.NewsPresenter by inject()
    private lateinit var newsAdapter: NewsAdapter
    val repositoryLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    var disableUserActionDialog: DisableUserActionsDialog = DisableUserActionsDialog()

    val repositoryList: MutableList<Articles> = mutableListOf()

    var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsPresenter.attachView( this )
        newsAdapter = NewsAdapter( mutableListOf<Articles>(),
            { userDetails: RepositoryOwnerDetails -> setUserDetailsClickListener(userDetails) },
            { Articles: Articles -> setArticlesClickListener( Articles ) }  )
        news_list.apply {
            layoutManager = repositoryLayoutManager
            adapter = newsAdapter
        }

        disableUserActionDialog = DisableUserActionsDialog()
    }

    private fun setArticlesClickListener(Articles: Articles) {
        val intent = Intent( this, RepositoryDetailsActivity::class.java )
        intent.putExtra("idRepository", Articles.author.toInt())
        startActivity(intent)
    }

    private fun setUserDetailsClickListener(userDetails: RepositoryOwnerDetails) {
        val intent = Intent( this, UsersActivity::class.java )
        intent.putExtra("login", userDetails.login)
        intent.putExtra("avatar_url", userDetails.avatar_url)
        intent.putExtra("repos_url", userDetails.repos_url)
        intent.putExtra("followers_url", userDetails.followers_url)
        intent.putExtra("site_admin", userDetails.site_admin)
        intent.putExtra("html_url", userDetails.html_url)
        startActivity(intent)
    }

    override fun setNews(repository: List<Articles>) {
        hideKeyboard(window.decorView)
        newsAdapter.setItems(repository)
        repositoryList.addAll(repository)
        loading = false
        if( disableUserActionDialog.isAdded || disableUserActionDialog.isVisible )
            disableUserActionDialog.dismiss()

        print("aaa" + repository.joinToString("-"))
        System.out.println("BBBB" + repository.joinToString("-"))
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        if( disableUserActionDialog.isAdded || disableUserActionDialog.isVisible )
            disableUserActionDialog.dismiss()
        loading = false
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun clearAdapterThatHasOldSearchData() {
        newsAdapter.notifyItemRangeRemoved(0, newsAdapter.getItems().size)
        newsAdapter.getItems().clear()
        repositoryList.clear()
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        newsPresenter.getNews(false)
    }

    override fun onPause() {
        super.onPause()
        newsPresenter.stopJobForGettingFreshNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        newsPresenter.deattachView(null)
    }

}