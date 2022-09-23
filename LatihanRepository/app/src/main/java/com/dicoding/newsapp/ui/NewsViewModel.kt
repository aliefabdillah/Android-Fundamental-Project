package com.dicoding.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    fun getHeadLineNews() = newsRepository.getHeadLineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity){
        //tanpa coroutine
//        newsRepository.setBookmarkNews(news, true)

        //dengan coroutine
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, true)
        }
    }

    fun deleteNews(news: NewsEntity){
        //tanpa coroutine
//        newsRepository.setBookmarkNews(news, false)

        //dengan corotuine
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, false)
        }
    }
}