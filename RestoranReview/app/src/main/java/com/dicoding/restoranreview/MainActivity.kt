package com.dicoding.restoranreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.restoranreview.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //configurasi layout dari recycler view dan item decoration
        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        //connect view model to activity
        val mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        //komponen observer data restaurant
        mainViewModel.restaurant.observe(
            this@MainActivity,
        ) { restaurant ->
            setRestaurantData(restaurant)
        }

        //komponen observer list customer review
        mainViewModel.listReview.observe(
            this@MainActivity,
        ){ customerReview ->
            /*
            * Variaebl customer review akan selalu di update secara realtime oleh live data
            * karena sudah menjadi bagian dari observer*/
            setReviewData(customerReview)
        }

        //komponen observer loading
        mainViewModel.isLoading.observe(
            this@MainActivity,
        ){
            showLoading(it)
        }

        //komponen observer snackbar
        mainViewModel.snackbarText.observe(this@MainActivity,) {
            /*Menampilkan snackbar
            *
            * getContentIfNotHandler merupakan method dari wrapper Event untuk mengecek apakah aksi
            * snackbar pernah dilakukan sebelumnya atau tidak pada live data
            *
            * Secara otomatis ketika aksi sudah pernah dilakukan sebelumnya, maka return dari method
            * akan menghasilkan null.*/
            it.getContentIfNotHandled()?.let { snackbarText ->
                Snackbar.make(window.decorView.rootView, snackbarText, Snackbar.LENGTH_SHORT).show()
            }
        }

        //handler button ketika di clikc
        binding.btnSend.setOnClickListener { view ->
            /*
            * menjalankan method postReview dari backgroudn thread
            * dengan parameter review input dari user*/
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    //menyimpan data restaurant ke view untuk data restaurant
    private fun setRestaurantData(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

//    menyimpan data review ke dalam view untuk listReview
    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>) {
        val listReview = consumerReviews.map {
            "${it.review}\n- ${it.name}"
        }
        val adapter = ReviewAdapter(listReview)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    //menampilkan animasi loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}