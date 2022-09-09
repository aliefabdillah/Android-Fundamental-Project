package com.dicoding.restoranreview

import com.google.gson.annotations.SerializedName

//class mengambil data response dari server seperti error dan message
data class RestaurantResponse(

	/*
	* Kemudian untuk menandai sebuah variabel terhubung dengan data JSON, gunakan
	* annotation @SerializedName*/
	@field:SerializedName("restaurant")
	val restaurant: Restaurant,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

//class restaurant untuk mengambil JSONObject dari Restaurant;
data class Restaurant(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,		//list CustomerReview dalam bentuk array

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)

//class CustomerReviewItem untuk mengambil JSON Array customerReviews
data class CustomerReviewsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("name")
	val name: String
)

data class PostReviewResponse(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
