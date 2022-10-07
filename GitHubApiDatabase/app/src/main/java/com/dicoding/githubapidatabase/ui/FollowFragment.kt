package com.dicoding.githubapidatabase.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapidatabase.databinding.FragmentFollowBinding
import com.dicoding.githubapidatabase.models.MainViewModel
import com.dicoding.githubapidatabase.adapter.UserFollsAdapter
import com.dicoding.githubapidatabase.data.api.Users

class FollowFragment : Fragment(){
    private lateinit var binding: FragmentFollowBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_NAME)
        val paramService = arguments?.getString(PARAM_SERVICE)
        if (username != null && paramService != null) {
            mainViewModel.getFolls(username, paramService)
            if (paramService == "followers"){
                mainViewModel.listFollowersData.observe(
                    viewLifecycleOwner
                ){ followers ->
                    createListFolls(followers)
                }
            }else{
                mainViewModel.listFollowingData.observe(
                    viewLifecycleOwner
                ){ following ->
                    createListFolls(following)
                }
            }
        }
    }

    private fun createListFolls(listFolls: List<Users>){
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserDetails.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUserDetails.addItemDecoration(itemDecoration)

        binding.rvUserDetails.setHasFixedSize(true)

        val detailsAdapter = UserFollsAdapter(listFolls)
        binding.rvUserDetails.adapter = detailsAdapter
        println("test")
        detailsAdapter.setOnItemClickCallback(object : UserFollsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                println("tes")
                val iToDetail = Intent(requireActivity(), DetailUserActivity::class.java)
                iToDetail.putExtra(DetailUserActivity.EXTRA_DATA, data)
                iToDetail.putExtra(DetailUserActivity.ACTIVITY_STATE, "Main")
                startActivity(iToDetail)
            }
        })
    }

    companion object {
        const val PARAM_SERVICE = "-1"
        var ARG_NAME = "username"
    }
}