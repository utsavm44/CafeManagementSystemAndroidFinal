package com.utsav.cafemanagementsystem.repository

import com.utsav.cafemanagementsystem.api.ApiRequest
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.response.AddtoCartResponse
import com.utsav.cafemanagementsystem.response.FoodItemResponse

class FoodItemRepository : ApiRequest() {
    private val FoodItemApi = ServiceBuilder.buildServices(com.utsav.cafemanagementsystem.api.FoodItemApi::class.java)

    suspend fun  getFoodItemApiData() : FoodItemResponse {
        return  apiRequest {
            FoodItemApi.displayFoodtem(ServiceBuilder.token!!)
        }
    }
    suspend fun getallAddtoCart(id:String): AddtoCartResponse {
        return apiRequest {
            FoodItemApi.getallProduct(ServiceBuilder.token!!,id!!)
        }
    }
}