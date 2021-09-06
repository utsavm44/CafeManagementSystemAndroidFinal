package com.utsav.cafemanagementsystem.api

import com.utsav.cafemanagementsystem.entity.AddCart
import com.utsav.cafemanagementsystem.response.AddtoCartResponse
import com.utsav.cafemanagementsystem.response.ForAddItemRespomse
import retrofit2.Response
import retrofit2.http.*

interface AddtoCartApi {
    @GET("fav/profuct/{id}")
    suspend fun getAllFavProduct(
        @Header("Authorization") token:String,
        @Path("id") userId:String
    ): Response<ForAddItemRespomse>

    @POST("add/fav")
    suspend fun AddFavtheProduct(
        @Header("Authorization") token:String,
        @Body addCart: AddCart
    ): Response<AddtoCartResponse>

    @GET("fav/product/by/{id}")
    suspend fun getParticularFavPRoduct(
        @Header("Authorization") token:String,
        @Path("id") userId:String
    ): Response<AddtoCartResponse>

    @DELETE("delete/bookmark/{PId}")
    suspend fun deleteFavPRoduct(
        @Header("Authorization") token:String,
        @Path("PId") PId:String
    ): Response<AddtoCartResponse>
}

