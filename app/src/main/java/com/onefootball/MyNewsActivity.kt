package com.onefootball

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.onefootball.model.News
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class MyNewsActivity : AppCompatActivity() {

    var jsonString : String? = null
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.newsRecyclerView)
        myAdapter = NewsAdapter()
        with(recyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MyNewsActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        var inputStream = assets.open("news.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        jsonString = buffer.toString(Charset.defaultCharset())

        val items = parseJsonString(jsonString!!)
        myAdapter.setNewsItems(items)
    }

    private fun parseJsonString(jsonString: String): List<News> {
        val mainObject = JSONObject(jsonString)
        val newsItems = mutableListOf<News>()
        val newsArray = mainObject.getJSONArray("news")
        newsArray.forEach {
            newsObject ->
            val title = newsObject.getString("title")
            val imageURL = newsObject.getString("image_url")
            val resourceName = newsObject.getString("resource_name")
            val resourceURL = newsObject.getString("resource_url")
            val newsLink = newsObject.getString("news_link")

            newsItems.add(News(title = title, imageURL = imageURL, resourceName = resourceName, resourceURL = resourceURL, newsLink = newsLink))
        }
        return newsItems
    }
}


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsItems = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = newsItems.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsItems[position]

        holder.titleView.text = news.title
        holder.newsView.load(url = news.imageURL)
        holder.resourceImage.load(url = news.resourceURL)
        holder.resourceName.text = news.resourceName
        holder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(news.newsLink))
            )
        }
    }

    fun setNewsItems(newListOfNewsItems: List<News>) {
        newsItems.clear()
        newsItems.addAll(newListOfNewsItems)
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var titleView: TextView = itemView.findViewById(R.id.news_title)
        var newsView: ImageView = itemView.findViewById(R.id.news_view)
        var resourceImage: ImageView = itemView.findViewById(R.id.resource_icon)
        var resourceName: TextView = itemView.findViewById(R.id.resource_name)
    }
}


fun JSONArray.forEach(jsonObject: (JSONObject) -> Unit) {
    for (index in 0 until this.length()) jsonObject(this[index] as JSONObject)
}