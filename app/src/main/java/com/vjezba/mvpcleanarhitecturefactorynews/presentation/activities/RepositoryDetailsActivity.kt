package com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vjezba.domain.entities.RepositoryDetails
import com.vjezba.domain.usecase.GithubContract
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.hide
import com.vjezba.mvpcleanarhitecturefactorynews.show
import kotlinx.android.synthetic.main.activity_repository_details.*
import kotlinx.android.synthetic.main.activity_repository_details.textUserName
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class RepositoryDetailsActivity : AppCompatActivity(), GithubContract.RepositoryDetailsView {

    var idRepository = 0L

    private val githubPresenter: GithubContract.RepositoryDetailsPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_details)
        githubPresenter.attachView( this )

        idRepository = intent.getLongExtra("idRepository", 0)
        githubPresenter.loadRepositoryDetailsById(idRepository)
    }

    override fun displayRepositoryDetails(repositoryDetails: RepositoryDetails) {

        // repository data
        textReposName.text = "Name of repo: " + repositoryDetails.name
        textDescription.text = "Description: " + repositoryDetails.description
        textStarGazers.text = "Star gazers count: " + repositoryDetails.stargazers_count
        textWatchers.text = "Watchers count: " + repositoryDetails.watchers_count
        textForks.text = "Forks count: " + repositoryDetails.forks
        textOpenIssue.text = "Number of issues: " + repositoryDetails.open_issues
        textLanguage.text = "Programming language name: " + repositoryDetails.language

        val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formatCreatedAtDate = sdf.format(repositoryDetails.created_at)
        val formatUpdatedAtDate = sdf.format(repositoryDetails.updated_at)
        textCreatedAt.text = "Created at: " + formatCreatedAtDate
        textUpdatedAt.text = "Updated at: " + formatUpdatedAtDate

        // user data
        textUserName.text = "Name of the user: " + repositoryDetails.owner.login
        textUserReposUrl.text = "Repositories url: " + repositoryDetails.owner.repos_url
        textUserHtmlUrl.text = "Html url: " + repositoryDetails.owner.html_url

        textDetailsRepository.setOnClickListener {
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
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this.baseContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        githubPresenter.deattachView(null)
    }
}