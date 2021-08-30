package io.teachmeskills.data.api.restApi


import io.teachmeskills.an03onl_accountingoffinancesapp.BuildConfig.API_KEY
import io.teachmeskills.data.api.response.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {

    @Headers("${API_KEY}")
    @GET("v1/fiat/map")
    suspend fun getCurrencyList(@Query("limit") limit: Int) : CurrencyResponse

}