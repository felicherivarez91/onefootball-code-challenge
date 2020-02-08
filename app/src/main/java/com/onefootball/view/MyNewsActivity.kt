package com.onefootball.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.onefootball.R
import com.onefootball.databinding.ActivityMainBinding
import com.onefootball.view.adapter.NewsAdapter
import com.onefootball.viewmodel.MyNewsViewModel
import com.onefootball.viewmodel.MyNewsViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class MyNewsActivity : AppCompatActivity() {

    @Inject
    lateinit var myNewsViewModelFactory: MyNewsViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private val newsAdapter: NewsAdapter = NewsAdapter()
    private lateinit var myNewsViewModel: MyNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myNewsViewModel = ViewModelProviders.of(this, myNewsViewModelFactory)
            .get(MyNewsViewModel::class.java)

        with(binding.newsRecyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MyNewsActivity)
        }

        myNewsViewModel.loadNews()
        myNewsViewModel.observeTheNews().observe(this, Observer {
            newsAdapter.setNewsItems(it)
        })
    }

}

