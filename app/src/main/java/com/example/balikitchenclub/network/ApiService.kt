package com.example.balikitchenclub.network

import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dto.CreateMenuDto
import com.example.balikitchenclub.network.dto.UpdateMenuDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login()

    @GET("menu")
    suspend fun getAllMenus(): Response<List<MenuResponseItem>>

    @POST("menu")
    suspend fun createMenu(
        @Body createMenuDto: CreateMenuDto
    ): Response<MenuResponseItem>

    @GET("menu/{id}")
    suspend fun getDetailMenu(
        @Path("id") id: Int
    ): Response<MenuResponseItem>

    @PATCH("menu/{id}")
    suspend fun updateMenu(
        @Path("id") id: Int,
        @Body() updateMenuDto: UpdateMenuDto
    ): Response<MenuResponseItem>

    @GET("sesi")
    suspend fun getAllSesi(): Response<List<SesiResponseItem>>
}