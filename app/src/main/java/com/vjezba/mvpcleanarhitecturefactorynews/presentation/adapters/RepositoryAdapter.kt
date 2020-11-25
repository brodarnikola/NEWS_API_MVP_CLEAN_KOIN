package com.vjezba.mvpcleanarhitecturefactorynews.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vjezba.domain.entities.Articles
import com.vjezba.domain.entities.RepositoryOwnerDetails
import com.vjezba.mvpcleanarhitecturefactorynews.R
import com.vjezba.mvpcleanarhitecturefactorynews.presentation.common.ListDiffer
import kotlinx.android.synthetic.main.repository_list.view.*


class RepositoryAdapter(var repositoryFiltered: MutableList<Articles>,
                        val userDetailsClickListener: (RepositoryOwnerDetails) -> Unit,
                        val ArticlesClickListener: (Articles) -> Unit )
    : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.imagePhoto
        val layoutParent: ConstraintLayout = itemView.parentLayout

        val title: TextView = itemView.textTitleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = repositoryFiltered[position]

        print("lockerList.joinToString")
        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error2)
            .fallback(R.drawable.error2)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.photo)

        holder.title.text = "Name: " + article.title

        holder.photo.setOnClickListener{
            userDetailsClickListener(RepositoryOwnerDetails("", "", "", "", "", false))
        }

        holder.layoutParent.setOnClickListener{
            ArticlesClickListener(article)
        }
    }

    fun updateDevices(updatedDevices: List<Articles>) {
        val listDiff = ListDiffer.getDiff(
            repositoryFiltered,
            updatedDevices,
            { old, new ->
                        old.author == new.author &&
                        old.title == new.title &&
                        old.description == new.description &&
                        old.url == new.url &&
                        old.urlToImage == new.urlToImage &&
                        old.publishedAt == new.publishedAt
            })

        for (diff in listDiff) {
            when (diff) {
                is ListDiffer.DiffInserted -> {
                    repositoryFiltered.addAll(diff.elements)
                    notifyItemRangeInserted(diff.position, diff.elements.size)
                }
                is ListDiffer.DiffRemoved -> {
                    //remove devices
                    for (i in (repositoryFiltered.size - 1) downTo diff.position) {
                        repositoryFiltered.removeAt(i)
                    }
                    notifyItemRangeRemoved(diff.position, diff.count)
                }
                is ListDiffer.DiffChanged -> {
                    repositoryFiltered[diff.position] = diff.newElement
                    notifyItemChanged(diff.position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return repositoryFiltered.size
    }

    fun setItems(data: List<Articles>) {
        repositoryFiltered.addAll(data)
        notifyDataSetChanged()
    }

    fun getItems() = repositoryFiltered

}