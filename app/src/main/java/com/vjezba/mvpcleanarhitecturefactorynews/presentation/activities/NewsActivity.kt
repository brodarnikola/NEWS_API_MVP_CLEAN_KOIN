package com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.RepositoryOwnerDetails
import com.vjezba.domain.usecase.NewsContract
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters.NewsAdapter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.dialog.ErrorMessageDialog
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.hide
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.show
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.ext.android.inject
import java.text.FieldPosition

class NewsActivity : AppCompatActivity(), NewsContract.NewsView {

    private val newsPresenter: NewsContract.NewsPresenter by inject()
    private lateinit var newsAdapter: NewsAdapter
    val newsLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    val newsList: MutableList<Articles> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsPresenter.attachView( this )

        newsAdapter = NewsAdapter( mutableListOf<Articles>(),
            { userDetails: RepositoryOwnerDetails -> setUserDetailsClickListener(userDetails) },
            { position: Int -> setArticlesClickListener( position ) }  )

        news_list.apply {
            layoutManager = newsLayoutManager
            adapter = newsAdapter
        }
    }

    private fun setArticlesClickListener( position: Int) {
        val intent = Intent( this, RepositoryDetailsActivity::class.java )
        intent.putExtra("listPosition", position)
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

    override fun setNews(articlesList: List<Articles>) {

        newsAdapter.setItems(articlesList)
        newsList.addAll(articlesList)

        print("aaa" + articlesList.joinToString("-"))
        System.out.println("BBBB" + articlesList.joinToString("-"))
    }

    override fun showMessage(message: String) {
        val errorMessageDialog = ErrorMessageDialog( )
        errorMessageDialog.show(supportFragmentManager, "")
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
        newsList.clear()
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