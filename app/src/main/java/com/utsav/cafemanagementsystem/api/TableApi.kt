package com.utsav.cafemanagementsystem.api

import com.utsav.cafemanagementsystem.entity.Table
import com.utsav.cafemanagementsystem.response.TableResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TableApi {
    @POST("addtable")
    suspend fun tableAdd(@Body tables: Table): Response<TableResponse>

}