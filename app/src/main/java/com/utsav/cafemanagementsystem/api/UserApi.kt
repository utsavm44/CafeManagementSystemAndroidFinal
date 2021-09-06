package com.utsav.cafemanagementsystem.api


import com.utsav.cafemanagementsystem.entity.User
import com.utsav.cafemanagementsystem.response.ImageResponse
import com.utsav.cafemanagementsystem.response.LoginResponse
import com.utsav.cafemanagementsystem.response.RegisterResponse
import com.utsav.cafemanagementsystem.response.UserUpdateResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("user/add")
    suspend fun userAdd(@Body users: User): Response<RegisterResponse>


    @POST("user/login")
    suspend fun checkUser(
      @Body user:User
    ): Response<LoginResponse>

    @GET("user/display/{id}")
    suspend fun viewUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
        // @Body user: User
    ): Response<LoginResponse>




    @PUT("user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body user: User
    ): Response<UserUpdateResponse>


    @Multipart
    @PUT("update/user/Profile/{id}")
    suspend fun  uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id:String,
        @Part file: MultipartBody.Part
    ): Response<ImageResponse>


}