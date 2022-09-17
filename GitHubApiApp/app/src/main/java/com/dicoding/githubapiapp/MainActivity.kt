package com.dicoding.githubapiapp

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubapiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager : RecyclerView.LayoutManager
        val itemDecoration : DividerItemDecoration
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(this, 2)
            binding.rvUser.layoutManager = layoutManager
            itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvUser.addItemDecoration(itemDecoration)

        }else{
            layoutManager = LinearLayoutManager(this)
            binding.rvUser.layoutManager = layoutManager
            itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvUser.addItemDecoration(itemDecoration)
        }

        binding.rvUser.setHasFixedSize(true)

        mainViewModel.listUserData.observe(
            this@MainActivity
        ){ listUser ->
            showSearchResult(listUser)
        }

        mainViewModel.isLoading.observe(
            this@MainActivity
        ){
            showLoading(it)
        }

        binding.searchBtn.setOnClickListener { view ->
            mainViewModel.findUser(binding.searchInputUser.text.toString())
        }

//        showRecyclerList()
    }

    private fun showSearchResult(resultUser: List<UserItems>){
        val adapter = ListUserAdapter(resultUser)
        binding.rvUser.adapter = adapter
        binding.searchInputUser.setText("")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

//    private fun showRecyclerList() {

//
//        val listUserAdapter = ListUserAdapter(list)
//        binding.rvUser.adapter = listUserAdapter
//
//        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
//            override fun onItemClicked(dataUser: User) {
//                val iToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
//                iToDetail.putExtra(DetailUserActivity.EXTRA_DATA, dataUser)
//                startActivity(iToDetail)
//            }
//        })
//    }
}