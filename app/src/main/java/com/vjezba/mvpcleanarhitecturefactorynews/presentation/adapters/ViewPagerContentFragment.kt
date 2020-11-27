package com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vjezba.mvpcleanarhitecturefactorynews.INTRO_STRING_OBJECT
import com.vjezba.mvpcleanarhitecturefactorynews.R
import kotlinx.android.synthetic.main.activity_repository_details.*
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.activity_user_details.tvTitle
import kotlinx.android.synthetic.main.news_view_pager_content.*

class ViewPagerContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.news_view_pager_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.takeIf { it.containsKey(INTRO_STRING_OBJECT) }?.apply {

            activity?.tvTitle?.text = getStringArray(INTRO_STRING_OBJECT)!![0]

            newsTitle.text = getStringArray(INTRO_STRING_OBJECT)!![0]
            Glide.with(this@ViewPagerContentFragment)
                .load(getStringArray(INTRO_STRING_OBJECT)!![1])
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error2)
                .fallback(R.drawable.error2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivNewsPicture)
            newsDescription.text = getStringArray(INTRO_STRING_OBJECT)!![2]
            //changeColor(getStringArray(INTRO_STRING_OBJECT)!![2])
        }

    }

    fun changeColor(color:String){
        when(color)
        {
            "R" ->
                base_layout.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            "B" ->
                base_layout.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
            "G" ->
                base_layout.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
        }
    }
}