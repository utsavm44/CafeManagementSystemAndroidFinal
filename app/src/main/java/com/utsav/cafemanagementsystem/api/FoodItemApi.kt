package com.utsav.cafemanagementsystem.api

import com.utsav.cafemanagementsystem.response.AddtoCartResponse
import com.utsav.cafemanagementsystem.response.FoodItemResponse
import com.utsav.cafemanagementsystem.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface FoodItemApi {
    @GET("fooditem/display")
    suspend fun displayFoodtem(
            @Header("Authorization") token : String

    ): Response<FoodItemResponse>

    @GET("api/food/single/{id}")
    suspend fun getallProduct(
        @Header("Authorization") token: String,
        @Path("id") userId:String
    ):Response<AddtoCartResponse>
    @Multipart
    @PUT("product/image/{id}")
    suspend fun uploadImage(
        @Header("Authorization") token:String,
        @Path("id") id:String,
        @Part file: MultipartBody.Part
    ): Response<LoginResponse>

}

