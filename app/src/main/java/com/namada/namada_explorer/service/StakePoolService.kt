package com.namada.namada_explorer.service

import com.namada.namada_explorer.model.BlockSignature
import com.namada.namada_explorer.model.Validators
import retrofit2.http.GET
import retrofit2.http.Path

interface StakePoolService {
    @GET("node/validators/list")
    suspend fun getValidator(): Validators

    @GET("node/validators/validator/{address}/latestSignatures")
    suspend fun getBlockSignature(@Path("address") address: String): List<BlockSignature>
}