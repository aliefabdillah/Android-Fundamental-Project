package com.dicoding.githubapiapp.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<Users>
)

@Parcelize
data class Users(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("html_url")
	val html_url: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int
): Parcelable

data class UsersDetailsResponse(

	@field:SerializedName("blog")
	val blog: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("location")
	val location: String?

)
