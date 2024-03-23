package com.namada.namada_explorer.service

import com.namada.namada_explorer.Block
import com.namada.namada_explorer.model.Page
import com.namada.namada_explorer.model.Transaction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RpcService {
    @GET("block")
    suspend fun getBlock(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Page<Block>

    @GET("tx")
    suspend fun getTransaction(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Page<Transaction>

    @GET("block/hash/{id}")
    suspend fun getBlock(
        @Path("id") id: String
    ): Block

    @GET("tx/{hash}")
    suspend fun getTransaction(
        @Path("hash") hash: String
    ): Transaction
}