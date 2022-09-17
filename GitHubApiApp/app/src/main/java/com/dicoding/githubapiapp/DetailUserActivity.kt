package com.dicoding.githubapiapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html.fromHtml
import com.bumptech.glide.Glide
import com.dicoding.githubapiapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailUser = intent.getParcelableExtra<Users>(EXTRA_DATA) as Users
        Glide.with(this@DetailUserActivity)
            .load(detailUser.avatarUrl)
            .circleCrop()
            .into(binding.imageDetail)

        binding.tvUsernameDetail.text = detailUser.login
//        binding.tvNameDetail.text = detailUser

//        binding.tvFollowersDetail.text = fromHtml("<b>${detailUser.}</b><br>Followers")
//        binding.tvFollowingsDetail.text = fromHtml("<b>${detailUser.following}</b><br>Followings")
//
//        binding.fieldRepo.text = ": ${detailUser.repositories}"
//        binding.fieldCompany.text = ": ${detailUser.company}"
//        binding.fieldLocation.text = ": ${detailUser.location}"
//        binding.fieldEmail.text = ": ${detailUser.email}"
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}