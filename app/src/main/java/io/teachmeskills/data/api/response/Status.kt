package io.teachmeskills.data.api.response

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("error_code") val errorCode: Int,
    @SerializedName("error_message") val errorMessage: String,
    @SerializedName("elapsed") val elapsed: Int,
    @SerializedName("credit_count") val creditCount: Int
)
