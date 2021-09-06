package com.utsav.cafemanagementsystem.repository

import com.utsav.cafemanagementsystem.api.ApiRequest
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.response.FoodMenuResponse


class FoodMenuRepository: ApiRequest() {
    private val FoodMenuApi = ServiceBuilder.buildServices(com.utsav.cafemanagementsystem.api.FoodMenuApi::class.java)

    suspend fun  getFoodMenuApiData(): FoodMenuResponse {
        return apiRequest {
            FoodMenuApi.displayMenu(ServiceBuilder.token!!)
        }
    }
}