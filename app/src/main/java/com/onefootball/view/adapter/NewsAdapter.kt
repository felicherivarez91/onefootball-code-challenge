package com.onefootball.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onefootball.databinding.NewsItemBinding
import com.onefootball.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsItems = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = newsItems.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsItems[position])
        holder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(newsItems[position].newsLink))
            )
        }
    }

    fun setNewsItems(items: List<News>) {
        newsItems.clear()
        newsItems.addAll(items)
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(private val itembind: NewsItemBinding) :
        RecyclerView.ViewHolder(itembind.root) {

        fun bind(news: News) {
            itembind.item = news
            itembind.executePendingBindings()
        }
    }
}