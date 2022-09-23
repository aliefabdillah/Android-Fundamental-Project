package com.dicoding.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.newsapp.BuildConfig
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.data.local.room.NewsDao
import com.dicoding.newsapp.data.remote.response.NewsResponse
import com.dicoding.newsapp.data.remote.retrofit.ApiService
import com.dicoding.newsapp.utils.AppExecutors
import retrofit2.Call
import retrofit2.Response

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
){

    /*
    * variabel mediatorLiveData digunakan jika ingin menggabungkan banyak sumber data dalam sebuah LiveData
    * apabila data sumber bukan merupakan LiveData gunakan fungsi setValue*/
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    // Menngambil data berita tanpa coroutine
/*    fun getHeadLineNews(): LiveData<Result<List<NewsEntity>>> {
        // inisialiasi dengan loading view
        result.value = Result.Loading
        val client = apiService.getNews(BuildConfig.API_KEY)        //mengambil data dari API

        client.enqueue(object : retrofit2.Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                //jika response berhasil di ambil
                if (response.isSuccessful){
                    val articles = response.body()?.articles        //mengambil data list articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            //mengambil data apakah sudah di bookmark atau belom
                            val isBookmarked = newsDao.isNewsBookmarked(article.title)
                            //memasukan data ke dalam entity
                            val news = NewsEntity(
                                article.title,
                                article.publishedAt,
                                article.urlToImage,
                                article.url,
                                isBookmarked
                            )
                            newsList.add(news)
                        }
                        //menghapus semua data yang tidak di bookmark
                        newsDao.deleteAll()
                        //memasukan data dari network ke dalam database
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                //status data yang diambil error
                result.value = Result.Error(t.message.toString())
            }
        })

        val localData = newsDao.getNews()   //mengambil data dari database

        //mengambil data dan memberikan return success
        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }

        return result
    }*/

    //mengambil data berita dengan corouine
    fun getHeadLineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        //emit digunakan jika sumber data bukan merupakan liveData
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { article ->
                val isBookmarked = newsDao.isNewsBookmarked(article.title)
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map { Result.Success(it) }
        //emit digunakan jika sumber data merupakan liveData
        emitSource(localData)
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    //method set Bookmark tanpa coroutine
/*    fun setBookmarkNews(news: NewsEntity, bookmarkState: Boolean){
        appExecutors.diskIO.execute{
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }*/

    //set Bookmark dengan coroutine
    suspend fun setBookmarkNews(news: NewsEntity, bookmarkState: Boolean){
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this){
                instance?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }

}