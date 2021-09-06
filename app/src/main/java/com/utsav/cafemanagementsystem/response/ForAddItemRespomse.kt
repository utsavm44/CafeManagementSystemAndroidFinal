package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.AddCart

class ForAddItemRespomse (
    val success:Boolean?=null,
    val msg:String?=null,
    val data:List<AddCart>?=null,
    val id:String?=null
)