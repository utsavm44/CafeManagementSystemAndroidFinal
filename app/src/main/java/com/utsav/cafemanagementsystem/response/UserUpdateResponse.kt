package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.User

data class UserUpdateResponse (
    val success: Boolean? = null,
    val data: User? = null,
    val id: String?=null
)