package com.example.balikitchenclub.network.dro

import com.google.gson.annotations.SerializedName

data class MenuResponseItem(
	val id: Int,
	val name: String,
	val description: String,
	val price: Int,
	val stock: Int,
	val image: String?,
	val category: MenuCategory
)

data class MenuCategory (
	val name: String
)

data class MenuResponse(
	val error: Boolean,
	val message: String,
	val data: List<MenuResponseItem>
)

data class MenuResponseDetail (
	val error: Boolean,
	val message: String,
	val data: MenuResponseItem
)

data class MenuResponseTransaction(
	var price: Int,
	var name: String,
	var id: Int,
	var stock: Int,
	var category: String,
	var qty: Int
)
