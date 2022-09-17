package com.dicoding.githubapiapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersDetailsResponse(

//	@field:SerializedName("twitter_username")
//	val twitterUsername: Any,
//
//	@field:SerializedName("bio")
//	val bio: Any,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("blog")
	val blog: String,

	@field:SerializedName("company")
	val company: String?,

//	@field:SerializedName("id")
//	val id: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

//	@field:SerializedName("gravatar_id")
//	val gravatarId: String,
//
//	@field:SerializedName("email")
//	val email: String?,
//
//	@field:SerializedName("organizations_url")
//	val organizationsUrl: String,
//
//	@field:SerializedName("hireable")
//	val hireable: Any,
//
//	@field:SerializedName("starred_url")
//	val starredUrl: String,
//
//	@field:SerializedName("followers_url")
//	val followersUrl: String,
//
//	@field:SerializedName("public_gists")
//	val publicGists: Int,
//
//	@field:SerializedName("url")
//	val url: String,
//
//	@field:SerializedName("received_events_url")
//	val receivedEventsUrl: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
//
//	@field:SerializedName("events_url")
//	val eventsUrl: String,
//
//	@field:SerializedName("html_url")
//	val htmlUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("location")
	val location: String?
//
//	@field:SerializedName("node_id")
//	val nodeId: String
): Parcelable
