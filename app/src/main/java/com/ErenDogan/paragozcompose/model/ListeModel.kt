package com.ErenDogan.paragozcompose.model

import com.google.gson.annotations.SerializedName

class ListeModel {
    @SerializedName("result")
    private val result: List<DovizModel>? = null
    fun getDataList(): List<DovizModel>? {
        return result
    }
}