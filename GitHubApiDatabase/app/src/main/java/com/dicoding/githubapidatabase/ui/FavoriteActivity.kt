package com.dicoding.githubapidatabase.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubapidatabase.R
import com.dicoding.githubapidatabase.adapter.ListFavoriteAdapter
import com.dicoding.githubapidatabase.data.local.UsersEntity
import com.dicoding.githubapidatabase.databinding.ActivityFavoriteBinding
import com.dicoding.githubapidatabase.models.FavoriteViewModel
import com.dicoding.githubapidatabase.models.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModelFactory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            viewModelFactory
        }

        favoriteViewModel.getFavoriteUsers().observe(this) { favoriteUsers ->
            binding.progressBarFavorite.visibility = View.GONE
            showFavoriteUsers(favoriteUsers)
        }
    }

    private fun showFavoriteUsers(favoriteUsers: List<UsersEntity>) {
        val layoutManagerFav : RecyclerView.LayoutManager
        val itemDecorationFav : DividerItemDecoration

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManagerFav = GridLayoutManager(this, 2)
            binding.rvUserFavorite.layoutManager = layoutManagerFav
            itemDecorationFav = DividerItemDecoration(this, layoutManagerFav.orientation)
            binding.rvUserFavorite.addItemDecoration(itemDecorationFav)
        }else{
            layoutManagerFav = LinearLayoutManager(this)
            binding.rvUserFavorite.layoutManager = layoutManagerFav
            itemDecorationFav = DividerItemDecoration(this, layoutManagerFav.orientation)
            binding.rvUserFavorite.addItemDecoration(itemDecorationFav)
        }

        binding.rvUserFavorite.setHasFixedSize(true)
        val adapterFav = ListFavoriteAdapter(favoriteUsers)
        binding.rvUserFavorite.adapter = adapterFav
        adapterFav.setOnItemClickCallback(object : ListFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersEntity) {
                val iToDetail = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                iToDetail.putExtra(DetailUserActivity.EXTRA_DATA, data)
                iToDetail.putExtra(DetailUserActivity.ACTIVITY_STATE, "Favorite")
                startActivity(iToDetail)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        val favoriteMenu = menu.findItem(R.id.favoriteMenu)
        favoriteMenu.isVisible = false

        val settingMenu = menu.findItem(R.id.settingMenu)
        settingMenu.setOnMenuItemClickListener {
            val  i = Intent(this, SettingActivity::class.java)
            startActivity(i)
            true
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}