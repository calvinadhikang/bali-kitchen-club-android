package com.example.balikitchenclub.network

import android.view.SurfaceControl.Transaction
import com.example.balikitchenclub.network.dro.BasicResponse
import com.example.balikitchenclub.network.dro.EmployeeResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseTransaction
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dro.TransactionResponseItem
import com.example.balikitchenclub.network.dto.CreateEmployeeDto
import com.example.balikitchenclub.network.dto.CreateMenuDto
import com.example.balikitchenclub.network.dto.CreateSesiDto
import com.example.balikitchenclub.network.dto.CreateTransactionDto
import com.example.balikitchenclub.network.dto.UpdateMenuDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login()

    @GET("menu")
    suspend fun getAllMenus(): Response<List<MenuResponseItem>>

    @GET("menu/transaction")
    suspend fun getAllMenuTransaction(): Response<List<MenuResponseTransaction>>

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

    @GET("sesi/now")
    suspend fun getSesiNow(): Response<SesiResponseItem>

    @GET("sesi/{id}")
    suspend fun getDetailSesi(
        @Path("id") id: Int
    ): Response<SesiResponseItem>

    @POST("sesi")
    suspend fun createSesi(
        @Body() createSesiDto: CreateSesiDto
    ): Response<SesiResponseItem>

    @GET("user/staffs")
    suspend fun getAllStaffs(): Response<List<EmployeeResponseItem>>

    @POST("user/register")
    suspend fun registerUser(
        @Body createEmployeeDto: CreateEmployeeDto
    ): Response<EmployeeResponseItem>

    @POST("transaction")
    suspend fun createTransaction(
        @Body createTransactionDto: CreateTransactionDto
    ): Response<CreateTransactionDto>

    @GET("transaction")
    suspend fun getTransactions(
        @Query("time") time: String = "today",
        @Query("sesi") sesi: Int = 0
    ): Response<List<TransactionResponseItem>>

    @GET("transaction/{id}")
    suspend fun getDetailTransaction(
        @Path("id") id: Int
    ): Response<TransactionResponseItem>

    @PATCH("transaction/{id}")
    suspend fun setTransactionLunas(
        @Path("id") id: Int
    ): Response<BasicResponse>
}