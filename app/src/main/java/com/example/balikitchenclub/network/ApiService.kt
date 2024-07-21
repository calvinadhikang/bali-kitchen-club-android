package com.example.balikitchenclub.network

import com.example.balikitchenclub.network.dro.AuthDro
import com.example.balikitchenclub.network.dro.BasicResponse
import com.example.balikitchenclub.network.dro.EmployeeResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseTransaction
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dro.StockDro
import com.example.balikitchenclub.network.dro.TransactionListDro
import com.example.balikitchenclub.network.dro.TransactionResponseItem
import com.example.balikitchenclub.network.dto.AuthDto
import com.example.balikitchenclub.network.dto.CreateEmployeeDto
import com.example.balikitchenclub.network.dto.CreateMenuDto
import com.example.balikitchenclub.network.dto.CreateSesiDto
import com.example.balikitchenclub.network.dto.CreateStockDto
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
    @POST("auth/login")
    suspend fun login(
        @Body authDto: AuthDto
    ): Response<AuthDro>

    @GET("menu")
    suspend fun getAllMenus(): Response<List<MenuResponseItem>>

    @GET("menu/transaction")
    suspend fun getAllMenuTransaction(): Response<List<MenuResponseTransaction>>

    @POST("menu/add")
    suspend fun createMenu(
        @Body createMenuDto: CreateMenuDto
    ): Response<BasicResponse>

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

    @PATCH("sesi/update/{id}")
    suspend fun updateSesi(
        @Path("id") id:Int,
        @Body() createSesiDto: CreateSesiDto
    ): Response<BasicResponse>

    @POST("sesi/add")
    suspend fun createSesi(
        @Body() createSesiDto: CreateSesiDto
    ): Response<BasicResponse>

    @GET("user/staffs")
    suspend fun getAllStaffs(): Response<List<EmployeeResponseItem>>

    @POST("user/register")
    suspend fun registerUser(
        @Body createEmployeeDto: CreateEmployeeDto
    ): Response<EmployeeResponseItem>

    @POST("transaction/add")
    suspend fun createTransaction(
        @Body createTransactionDto: CreateTransactionDto
    ): Response<BasicResponse>

    @GET("transaction")
    suspend fun getTransactions(
        @Query("time") time: String = "today",
        @Query("sesi") sesi: Int = 0
    ): Response<TransactionListDro>

    @GET("transaction/{id}")
    suspend fun getDetailTransaction(
        @Path("id") id: Int
    ): Response<TransactionResponseItem>

    @PATCH("transaction/{id}")
    suspend fun setTransactionLunas(
        @Path("id") id: Int
    ): Response<BasicResponse>

    @GET("stock/{id}")
    suspend fun getStockMutationByMenuId(
        @Path("id") id: Int
    ): Response<List<StockDro>>

    @POST("stock/add")
    suspend fun addStockByMenuId(
        @Body createStockDto: CreateStockDto
    ): Response<BasicResponse>
}