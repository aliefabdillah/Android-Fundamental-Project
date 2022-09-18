package com.dicoding.githubapiapp.models

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubapiapp.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.ARG_NAME, username)
            when(position){
                0 -> putString(FollowFragment.PARAM_SERVICE, "followers")
                1 -> putString(FollowFragment.PARAM_SERVICE, "following")
            }
        }

        return fragment
    }

}