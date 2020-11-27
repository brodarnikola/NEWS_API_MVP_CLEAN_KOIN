package com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vjezba.domain.entities.Articles
import com.vjezba.mvpcleanarhitecturefactorynews.R
import kotlinx.android.synthetic.main.news_list.view.*

class NewsAdapter(var articlesList: MutableList<Articles>,
                  val ArticlesClickListener: (Int) -> Unit )
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.imagePhoto
        val layoutParent: LinearLayout = itemView.parentLayout

        val title: TextView = itemView.textTitleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindItem(holder, articlesList[position], position)
    }

    private fun bindItem(holder: ViewHolder, article: Articles, position: Int) {

        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error2)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.photo)

        holder.title.text = "Name: " + article.title

        holder.layoutParent.setOnClickListener{
            ArticlesClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    fun setItems(data: List<Articles>) {
        articlesList.addAll(data)
        notifyDataSetChanged()
    }

    fun getItems() = articlesList

}