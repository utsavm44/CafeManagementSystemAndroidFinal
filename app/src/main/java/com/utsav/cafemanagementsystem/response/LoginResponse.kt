package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.User

data class LoginResponse (
    val success:Boolean?=null,
    val token:String?=null,
    val msg:String?=null,
    val data:List<User> ?=null,
    val id:String?=null
)