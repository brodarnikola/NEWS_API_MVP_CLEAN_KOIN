package com.vjezba.mvpcleanarhitecturefactorynews.presentation.fragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.usecase.NewsDetailsUseCases
import com.vjezba.domain.usecase.NewsUseCases
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters.NewsSlidePagerAdapter
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.hide
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.show
import kotlinx.android.synthetic.main.fragment_news_view_pager.*
import org.koin.android.ext.android.inject


class IntroViewPagerFragment : Fragment(), NewsDetailsUseCases.NewsDetailsView {

    private val newsPresenter: NewsDetailsUseCases.NewsDetailsPresenter by inject()

    private var firstInitNewsPosition = 0

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment.id == R.id.fragmentData) {
            val b = Bundle()
            b.putInt("listPosition", 0)
            fragment.arguments = b
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsPresenter.attachView( this )

        val b = arguments
        if (b != null) {
            firstInitNewsPosition = b.getInt("listPosition")
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsPresenter.loadNewsFromRoom()
    }

    override fun displayNewsDetails(newsDetails: List<Articles>) {

        val pagerAdapter =
            NewsSlidePagerAdapter(
                this,
                getListOfNewsPagerContents(newsDetails),
                newsDetails.size
            )
        news_pager.adapter = pagerAdapter

        Handler().postDelayed({
            news_pager.setCurrentItem(firstInitNewsPosition, false)
        }, 100)

        TabLayoutMediator(tab_layout, news_pager)
        { tab, position -> }.attach()
    }

    private fun getListOfNewsPagerContents(newsDetails: List<Articles>): List<Array<String>> {
        val articlesDetailsList = newsDetails.map { arrayOf(it.title, it.urlToImage, it.description) }
        return articlesDetailsList
    }

    override fun showMessage(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        newsPresenter.deattachView(null)
    }

}
