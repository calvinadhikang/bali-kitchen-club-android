package com.example.balikitchenclub.network.dro

import com.google.gson.annotations.SerializedName

data class MenuResponseItem(

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("category")
	val category: String
)

data class MenuResponseTransaction(
	var price: Int,
	var name: String,
	var id: Int,
	var stock: Int,
	var category: String,
	var qty: Int
)
