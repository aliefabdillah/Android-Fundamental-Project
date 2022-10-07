package com.dicoding.githubapidatabase.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html.fromHtml
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.githubapidatabase.R
import com.dicoding.githubapidatabase.data.api.Users
import com.dicoding.githubapidatabase.data.api.UsersDetailsResponse
import com.dicoding.githubapidatabase.databinding.ActivityDetailUserBinding
import com.dicoding.githubapidatabase.models.FavoriteViewModel
import com.dicoding.githubapidatabase.models.MainViewModel
import com.dicoding.githubapidatabase.adapter.SectionsPagerAdapter
import com.dicoding.githubapidatabase.data.local.UsersEntity
import com.dicoding.githubapidatabase.models.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityState = intent.getStringExtra(ACTIVITY_STATE)

        val loginUsername: String
        var detailUsers: Users? = null

        if (activityState.equals("Main")){
            val detailsUser = intent.getParcelableExtra<Users>(EXTRA_DATA) as Users
            Glide.with(this@DetailUserActivity)
                .load(detailsUser.avatarUrl)
                .circleCrop()
                .into(binding.imageDetail)
            binding.tvUsernameDetail.text = detailsUser.login

            loginUsername = detailsUser.login
            detailUsers = detailsUser
        }else{
            val detailsUser = intent.getParcelableExtra<UsersEntity>(EXTRA_DATA) as UsersEntity
            Glide.with(this@DetailUserActivity)
                .load(detailsUser.avatarUrl)
                .circleCrop()
                .into(binding.imageDetail)
            binding.tvUsernameDetail.text = detailsUser.login

            loginUsername = detailsUser.login
        }

        mainViewModel.getUserDetails(loginUsername)

        mainViewModel.isLoading.observe(this){
            showLoadingDetails(it)
        }

        mainViewModel.detailUsers.observe(this){
            details -> setDataDetails(details)
        }

        createTabsLayout(loginUsername)

        //create favorite viewModel using ViewModelFactory
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this@DetailUserActivity)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        //cek kondisi apakah user sudah masuk ke dalam favorite atau belum
        favoriteViewModel.checkUserInDb(loginUsername)
        favoriteViewModel.favoriteState.observe(this){ isFavorite ->
            if (isFavorite){
                binding.btnAddFavorite.text = getString(R.string.removeFromFavorite)
                binding.btnAddFavorite.background.setTint(ContextCompat.getColor(this, R.color.dark_navy))
                binding.btnAddFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_white_24, 0, 0, 0)

                binding.btnAddFavorite.setOnClickListener {
                    favoriteViewModel.deleteFromFavorite(loginUsername)
                }
            }else{
                binding.btnAddFavorite.text = getString(R.string.addtofavorite)
                binding.btnAddFavorite.background.setTint(ContextCompat.getColor(this, R.color.redHeart))
                binding.btnAddFavorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_border_white_24, 0, 0, 0)

                binding.btnAddFavorite.setOnClickListener {
                    if (detailUsers != null) {
                        favoriteViewModel.saveFavorite(detailUsers)
                    }
                }
            }
        }
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

    @SuppressLint("StringFormatMatches")
    private fun setDataDetails(details: UsersDetailsResponse){

        if (details.name.isNullOrBlank()){
            binding.tvNameDetail.text = "-"
        }else{
            binding.tvNameDetail.text = details.name
        }
        binding.tvFollowersDetail.text = fromHtml("<b>${details.followers}</b><br>Followers")
        binding.tvFollowingsDetail.text = fromHtml("<b>${details.following}</b><br>Followings")

        binding.fieldRepo.text = getString(R.string.viewDetailUserText, details.publicRepos)

        if (details.company.isNullOrBlank()){
            binding.fieldCompany.text = ": -"
        }else{
            binding.fieldCompany.text = getString(R.string.viewDetailUserText, details.company)
        }

        if (details.location.isNullOrBlank()){
            binding.fieldLocation.text = ": -"
        }else{
            binding.fieldLocation.text = getString(R.string.viewDetailUserText, details.location)
        }

        if (details.blog.isNullOrBlank()){
            binding.fieldBlog.text = ": -"
        }else{
            binding.fieldBlog.text = getString(R.string.viewDetailUserText, details.blog)
        }
    }

    private fun createTabsLayout(username: String){
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.followTabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoadingDetails(isLoading: Boolean) {
        binding.progressBarDetails.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ACTIVITY_STATE = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings
        )
    }
}