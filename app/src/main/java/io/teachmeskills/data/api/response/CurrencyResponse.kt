package io.teachmeskills.data.api.response

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("data") val data: List<CurrencyEntity>,
    @SerializedName("status") val status: Status
)
