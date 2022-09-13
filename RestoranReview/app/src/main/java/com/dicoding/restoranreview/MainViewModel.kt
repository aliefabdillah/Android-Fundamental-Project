package com.dicoding.restoranreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    /*
    * BACKING PROPERTY
    *
    *  dengan membuat data yang bertipe MutableLiveData menjadi private (_listReview) dan yang
    * bertipe LiveData menjadi public (listReview). Cara ini disebut dengan backing property.
    * Dengan begitu Anda dapat mencegah variabel yang bertipe MutableLiveData diubah dari luar class.
    * Karena memang seharusnya hanya ViewModel-lah yang dapat mengubah data.*/
    private val _restaturant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaturant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //variabel live data untuk snack bar
    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    companion object {
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)     //memanggil retrofit

        /* enqueue berfungsi untuk menjalakan request asynchronus di background agar app tidak freezing/lag */
        client.enqueue(object: retrofit2.Callback<RestaurantResponse> {
            //jika callbak memiliki response
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _isLoading.value = false
                //cek apakah server mengembalikan kode 200 (OK)
                if (response.isSuccessful) {
//                    val responseBody = response.body()                  //mengambil data dari body API
                    _restaturant.value = response.body()?.restaurant                    //menyimpan nilai restaurant dari API ke variabel livedata
                    _listReview.value = response.body()?.restaurant?.customerReviews    // menyimpan nilai review dari API ke variabel livedata
//                    if (responseBody != null) {
//                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            //ketika callbak failure
            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun postReview(review: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)

        client.enqueue(object : retrofit2.Callback<PostReviewResponse>{
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listReview.value = response.body()?.customerReviews        //menyimpan nilai customer review dari API ke variabel live data
                    _snackbarText.value = response.body()?.message              //menampilkan pesan dari API ketika berhasil Post Review
                }else{
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}