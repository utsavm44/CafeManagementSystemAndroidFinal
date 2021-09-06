package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.User


data class RegisterResponse (
    val success: Boolean? = null,
    val data: User? =null
)