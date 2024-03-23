package com.namada.namada_explorer.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BlockSignature(
    @SerializedName("block_number")
    val blockNumber: Long,

    @SerializedName("sign_status")
    val signStatus: Boolean
) : Parcelable