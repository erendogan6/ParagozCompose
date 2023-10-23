package com.ErenDogan.paragozcompose.model
import com.google.gson.annotations.SerializedName

data class DovizModel(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("sellingstr")
    var sellingstr: String? = null,

    @SerializedName("code")
    var code:String? = null,

    @SerializedName("buyingstr")
    var buyingstr: String? = null)
