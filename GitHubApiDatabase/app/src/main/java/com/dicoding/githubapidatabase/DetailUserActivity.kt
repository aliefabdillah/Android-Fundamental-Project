package com.dicoding.githubapidatabase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html.fromHtml
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.dicoding.githubapidatabase.api.Users
import com.dicoding.githubapidatabase.api.UsersDetailsResponse
import com.dicoding.githubapidatabase.databinding.ActivityDetailUserBinding
import com.dicoding.githubapidatabase.models.MainViewModel
import com.dicoding.githubapidatabase.models.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailsUser= intent.getParcelableExtra<Users>(EXTRA_DATA) as Users
        Glide.with(this@DetailUserActivity)
            .load(detailsUser.avatarUrl)
            .circleCrop()
            .into(binding.imageDetail)
        binding.tvUsernameDetail.text = detailsUser.login

        mainViewModel.getUserDetails(detailsUser.login)

        mainViewModel.isLoading.observe(this){
            showLoadingDetails(it)
        }

        mainViewModel.detailUsers.observe(this){
            details -> setDataDetails(details)
        }

        createTabsLayout(detailsUser.login)

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

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings
        )
    }
}