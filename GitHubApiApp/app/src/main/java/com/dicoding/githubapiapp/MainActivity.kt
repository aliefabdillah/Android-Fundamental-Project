package com.dicoding.githubapiapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
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

        mainViewModel.toastText.observe(
            this@MainActivity
        ){
           it.getContentIfNotHandled()?.let { toastText ->
               Toast.makeText(this@MainActivity, "Username Not Found", Toast.LENGTH_LONG).show()
           }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Masukan Username"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.findUser(query)
                }else{
                    Toast.makeText(this@MainActivity, "Input Cannot Be Empty", Toast.LENGTH_LONG).show()
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    private fun showSearchResult(resultUser: List<Users>){


        val adapter = ListUserAdapter(resultUser)
        binding.rvUser.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                val iToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                iToDetail.putExtra(DetailUserActivity.EXTRA_DATA, data)
                startActivity(iToDetail)
            }
        })
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