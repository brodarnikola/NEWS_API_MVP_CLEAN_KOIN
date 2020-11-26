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
import com.vjezba.domain.usecase.GithubContract
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.hide
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters.RepositoryAdapter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.dialog.DisableUserActionsDialog
import com.vjezba.mvpcleanarhitecturefactorynews.show
import kotlinx.android.synthetic.main.activity_repositories.*
import org.koin.android.ext.android.inject

class NewsActivity : AppCompatActivity(), GithubContract.RepositoryView {

    private val githubPresenter: GithubContract.RepositoryPresenter by inject()
    private lateinit var repositoryAdapter: RepositoryAdapter
    val repositoryLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    var disableUserActionDialog: DisableUserActionsDialog = DisableUserActionsDialog()

    val repositoryList: MutableList<Articles> = mutableListOf()

    var loading = false

    var keyword: String = "Kotlin"
    var sort: String = ""
    var order: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        githubPresenter.attachView( this )
        repositoryAdapter = RepositoryAdapter( mutableListOf<Articles>(),
            { userDetails: RepositoryOwnerDetails -> setUserDetailsClickListener(userDetails) },
            { Articles: Articles -> setArticlesClickListener( Articles ) }  )
        repository_list.apply {
            layoutManager = repositoryLayoutManager
            adapter = repositoryAdapter
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

    override fun setRepository(repository: List<Articles>) {
        hideKeyboard(window.decorView)
        repositoryAdapter.setItems(repository)
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

    /*override fun startSearch(mKeyword: String, mSort: String, mOrder: String, showOtherData: Boolean) {
        System.out.println("Keyword is: ${mKeyword}, sort is: ${mSort}, order is: ${mOrder}")

        keyword = mKeyword
        sort = mSort
        order = mOrder

        githubPresenter.isNewSearchNewQueryForRepositoriesStarted(showOtherData)
        githubPresenter.getRepositories(keyword, sort, order, showOtherData)
    }*/

    override fun clearAdapterThatHasOldSearchData() {
        repositoryAdapter.notifyItemRangeRemoved(0, repositoryAdapter.getItems().size)
        repositoryAdapter.getItems().clear()
        repositoryList.clear()
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        githubPresenter.getRepositories("Kotlin", sort, order, false)
    }

    override fun onPause() {
        super.onPause()
        githubPresenter.stopJobForGettingFreshNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        githubPresenter.deattachView(null)
    }

}