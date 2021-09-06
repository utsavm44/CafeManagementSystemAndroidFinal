package com.utsav.cafemanagementsystem.repository

import com.utsav.cafemanagementsystem.api.ApiRequest
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.api.TableApi
import com.utsav.cafemanagementsystem.entity.Table
import com.utsav.cafemanagementsystem.response.TableResponse

class TableRepository : ApiRequest(){
    val myApi= ServiceBuilder.buildServices(TableApi::class.java)

    suspend fun registerTable(table: Table): TableResponse {
        return apiRequest {
            myApi.tableAdd(table)
        }
    }
}