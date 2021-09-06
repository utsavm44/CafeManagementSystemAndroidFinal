package com.utsav.cafemanagementsystem.repository

import com.utsav.cafemanagementsystem.api.AddtoCartApi
import com.utsav.cafemanagementsystem.api.ApiRequest
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.entity.AddCart
import com.utsav.cafemanagementsystem.response.AddtoCartResponse
import com.utsav.cafemanagementsystem.response.ForAddItemRespomse

class AddToCartRepository : ApiRequest(){
    val myApi= ServiceBuilder.buildServices(AddtoCartApi::class.java)
    suspend fun getallFavFoodItem(id:String): ForAddItemRespomse {
        return apiRequest {
            myApi.getAllFavProduct(ServiceBuilder.token!!,id)
        }
    }
    suspend fun AddFav(addFav: AddCart): AddtoCartResponse {
        return apiRequest {
            myApi.AddFavtheProduct(ServiceBuilder.token!!,addFav)
        }
    }
//    suspend fun getParticularNote():AddtoCartResponse{
//        return apiRequest {
//            myApi.getParticularFavPRoduct(ServiceBuilder.token!!,ServiceBuilder.id!!)
//        }
//    }
    suspend fun deleteFavProduct(noteId:String): AddtoCartResponse {
        return apiRequest {
            myApi.deleteFavPRoduct(ServiceBuilder.token!!,noteId)
        }
    }
}