package com.example.tapptic.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @get:GET("test/json.php")
    val numbers: Call<List<NumberLight>>

    @GET("test/json.php?name=")
    fun getNumberDetails(@Query("name") name: String): Call<NumberLightDetails>

}
