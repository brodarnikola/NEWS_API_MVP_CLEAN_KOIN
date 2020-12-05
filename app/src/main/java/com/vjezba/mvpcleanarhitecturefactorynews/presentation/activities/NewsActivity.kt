package com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.usecase.NewsUseCases
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters.NewsAdapter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.dialog.ErrorMessageDialog
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.hide
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.show
import kotlinx.android.synthetic.main.activity_news.*
import org.koin.android.ext.android.inject

class NewsActivity : AppCompatActivity(), NewsUseCases.NewsView {

    private val newsPresenter: NewsUseCases.NewsPresenter by inject()
    private lateinit var newsAdapter: NewsAdapter
    val newsLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    val newsList: MutableList<Articles> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsPresenter.attachView( this )

        newsAdapter = NewsAdapter( mutableListOf<Articles>(),
            { position: Int -> setArticlesClickListener( position ) }  )

        news_list.apply {
            layoutManager = newsLayoutManager
            adapter = newsAdapter
        }
    }

    private fun setArticlesClickListener( position: Int) {
        val intent = Intent( this, NewsDetailsActivity::class.java )
        intent.putExtra("listPosition", position)
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

    override fun clearAdapterThatHasOldData() {
        newsAdapter.notifyItemRangeRemoved(0, newsAdapter.getItems().size)
        newsAdapter.getItems().clear()
        newsList.clear()
    }

    override fun onResume() {
        super.onResume()
        if( newsAdapter.getItems().isNotEmpty() ) {
            newsPresenter.getNews(true)
        }
        else {
            newsPresenter.getNews(false)
        }
    }

    override fun onPause() {
        super.onPause()
        newsPresenter.stopJobForGettingFreshNews()
    }

    override fun onDestroy() {
        newsPresenter.deattachView(null)
        super.onDestroy()
    }

}