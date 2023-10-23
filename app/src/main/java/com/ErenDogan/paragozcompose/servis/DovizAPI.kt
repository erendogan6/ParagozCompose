package com.ErenDogan.paragozcompose.servis

import com.ErenDogan.paragozcompose.model.ListeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


interface DovizAPI {
    @GET(
        "economy/allCurrency"
    )
    @Headers("Content-Type: application/json", "Authorization: apikey 6rge3n45OJUCPY4Fp5bQQM:52rd0hBxjBZgT6oac7tDeq")
    fun data(): Call<ListeModel>
}