package com.example.retrofit.Model

import com.google.gson.annotations.SerializedName

data class LoverModel(

    val id:Int,
    val name:String?="",
    val yearold:Int,
    val image:String?="",
    val type:String?=""
) {
}

