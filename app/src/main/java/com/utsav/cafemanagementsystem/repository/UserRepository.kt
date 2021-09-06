package com.utsav.cafemanagementsystem.repository

import com.utsav.cafemanagementsystem.api.ApiRequest
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.api.UserApi
import com.utsav.cafemanagementsystem.entity.User
import com.utsav.cafemanagementsystem.response.ImageResponse

import com.utsav.cafemanagementsystem.response.LoginResponse
import com.utsav.cafemanagementsystem.response.RegisterResponse
import com.utsav.cafemanagementsystem.response.UserUpdateResponse
import okhttp3.MultipartBody


class UserRepository: ApiRequest() {
    val myApi = ServiceBuilder.buildServices(UserApi::class.java)

    suspend fun registerUSer(user: User): RegisterResponse {
        return apiRequest {
            myApi.userAdd(user)
        }
    }

    suspend fun checkUser(user: User): LoginResponse {
        return apiRequest {
            myApi.checkUser(user)
        }
    }

    suspend fun getUser(id: String): LoginResponse {
        return apiRequest {
            myApi.viewUser(ServiceBuilder.token!!, id)
        }
    }


    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            myApi.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }


    suspend fun updateUser(user: User): UserUpdateResponse {
        return apiRequest {
            myApi.updateUser(ServiceBuilder.token!!, user)
        }
    }
}



