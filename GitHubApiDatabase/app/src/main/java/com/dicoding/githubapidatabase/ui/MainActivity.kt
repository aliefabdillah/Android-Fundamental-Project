package com.dicoding.githubapidatabase.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubapidatabase.R
import com.dicoding.githubapidatabase.data.api.Users
import com.dicoding.githubapidatabase.databinding.ActivityMainBinding
import com.dicoding.githubapidatabase.adapter.ListUserAdapter
import com.dicoding.githubapidatabase.models.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
               Toast.makeText(this@MainActivity, toastText, Toast.LENGTH_LONG).show()
           }
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        mainViewModel.findUser(query)
                    }else{
                        Toast.makeText(this@MainActivity, "Input Cannot Be Empty", Toast.LENGTH_LONG).show()
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favoriteMenu -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
            }
            R.id.settingMenu -> {
                val  i = Intent(this, SettingActivity::class.java)
                startActivity(i)
            }
        }
        return true
    }

    private fun showSearchResult(resultUser: List<Users>){
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

        val adapter = ListUserAdapter(resultUser)
        binding.rvUser.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                val iToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                iToDetail.putExtra(DetailUserActivity.EXTRA_DATA, data)
                iToDetail.putExtra(DetailUserActivity.ACTIVITY_STATE, "Main")
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
}