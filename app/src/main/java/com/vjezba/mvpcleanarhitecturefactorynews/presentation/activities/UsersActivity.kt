package com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vjezba.domain.usecase.GithubContract
import com.vjezba.mvpcleanarhitecturefactorynews.R
import kotlinx.android.synthetic.main.activity_user_details.*
import org.koin.android.ext.android.inject


class UsersActivity : AppCompatActivity(), GithubContract.UserView  {

    var login = ""
    var avatarUrl = ""
    var reposUrl = ""
    var followersUrl = ""
    var siteAdmin = false
    var html_url = ""

    private val githubPresenter: GithubContract.UserPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        githubPresenter.attachView( this )

        login = intent.getStringExtra("login")
        avatarUrl = intent.getStringExtra("avatar_url")
        reposUrl = intent.getStringExtra("repos_url")
        followersUrl = intent.getStringExtra("followers_url")
        siteAdmin = intent.getBooleanExtra("site_admin", false)
        html_url = intent.getStringExtra("html_url")

        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error2)
            .fallback(R.drawable.error2)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivUserImage)

        textUserName.text = "Username: " + login
        textReposUrl.text = "Repos url: "  + reposUrl
        textFollowersUrl.text = "Follower url: "  + followersUrl
        textSiteAdmin.text = "Is this user admin in github: "  + siteAdmin
        texUserLink.text = "Link to see details about this user: "  + html_url

        texUserLink.setOnClickListener {
            githubPresenter.startToDisplayUserDetailsInBrowser( html_url)
        }

        textReposUrl.setOnClickListener {
            githubPresenter.startToDisplayUserDetailsInBrowser( reposUrl)
        }
    }

    override fun showUserDetailInExternalBrowser( urlToLoad: String) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(urlToLoad))
        startActivity(browserIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        githubPresenter.deattachView(null)
    }


}
