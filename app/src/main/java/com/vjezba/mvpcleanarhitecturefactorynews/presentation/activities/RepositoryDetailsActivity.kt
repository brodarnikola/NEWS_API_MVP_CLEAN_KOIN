package com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters.IntroViewPagerFragment
import kotlinx.android.synthetic.main.activity_repository_details.*

class RepositoryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_details)
    }

    override fun onStart() {
        super.onStart()

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val fragment = IntroViewPagerFragment()
        val bundle = Bundle()
        bundle.putString("title", "aa")
        bundle.putString("urlToImage", "bb")
        bundle.putString("description", "cc")
        fragment.setArguments(bundle)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentData, fragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}