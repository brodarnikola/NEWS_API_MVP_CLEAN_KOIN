package com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.RepositoryDetails
import com.vjezba.domain.usecase.NewsContract
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.hide
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.utils.show
import kotlinx.android.synthetic.main.activity_repository_details.*
import kotlinx.android.synthetic.main.fragment_news_view_pager.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class IntroViewPagerFragment : Fragment(), NewsContract.RepositoryDetailsView {

    private val newsPresenter: NewsContract.RepositoryDetailsPresenter by inject()

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment.id == R.id.fragmentData) {
            val b = Bundle()
            b.putString("urlToImage", "Message")
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
            Toast.makeText(
                requireContext(),
                b.getString("urlToImage"),
                Toast.LENGTH_SHORT
            ).show()
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsPresenter.loadNewsFromRoom()
    }

    /*fun getListOfPagerContents(): List<Array<String>> {

        val ar1 = arrayOf(getString(R.string.intro_title_1), getString(R.string.intro_sub_title_1),"R" )
        val ar2 = arrayOf(getString(R.string.intro_title_2), getString(R.string.intro_sub_title_2) ,"G")
        val ar3 = arrayOf(getString(R.string.intro_title_3), getString(R.string.intro_sub_title_3) ,"B")
        return listOf(ar1,ar2,ar3)
    }*/

    override fun displayRepositoryDetails(repositoryDetails: RepositoryDetails) {

        // repository data
        //textReposName.text = "Name of repo: " + repositoryDetails.name


        val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formatCreatedAtDate = sdf.format(repositoryDetails.created_at)
        val formatUpdatedAtDate = sdf.format(repositoryDetails.updated_at)

        /*textDetailsRepository.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(repositoryDetails.html_url))
            startActivity(browserIntent)
        }

        btnOpenUserData.setOnClickListener {
            val intent = Intent( this, UsersActivity::class.java )
            intent.putExtra("login", repositoryDetails.owner.login)
            intent.putExtra("avatar_url", repositoryDetails.owner.avatar_url)
            intent.putExtra("repos_url", repositoryDetails.owner.repos_url)
            intent.putExtra("followers_url", repositoryDetails.owner.followers_url)
            intent.putExtra("site_admin", repositoryDetails.owner.site_admin)
            intent.putExtra("html_url", repositoryDetails.owner.html_url)
            startActivity(intent)
            finish()
        }*/
    }

    override fun displayNewsDetails(newsDetails: List<Articles>) {

        //activity?.tvTitle?.text = newsDetails[0].title

        val pagerAdapter =
            NewsSlidePagerAdapter(this, getListOfNewsPagerContents(newsDetails), newsDetails.size)
        news_pager.adapter = pagerAdapter

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
