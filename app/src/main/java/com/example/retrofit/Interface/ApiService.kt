package com.example.retrofit.Interface

import com.example.retrofit.Model.LoverModel
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("Lover")
    suspend fun getAll() :Response< List<LoverModel>>

    @DELETE("Lover/{id}")
    suspend fun Delete(@Path("id") id:String):Response<JsonObject>


    @POST("Lover")
    suspend fun creatLover(@Body body:JsonObject):Response<JsonObject>

    @PUT("Lover/{id}")
    suspend fun updateLover(@Path("id") id:String, @Body body: JsonObject):Response<JsonObject>
}