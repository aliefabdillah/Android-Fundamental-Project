package com.dicoding.newsapp.ui

import androidx.lifecycle.ViewModel
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.local.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    fun getHeadLineNews() = newsRepository.getHeadLineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity){
        newsRepository.setBookmarkNews(news, true)
    }

    fun deleteNews(news: NewsEntity){
        newsRepository.setBookmarkNews(news, false)
    }
}