package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.ForAddItem

class AddtoCartResponse (val success:Boolean?=null,
                         val data:List<ForAddItem>?=null,
                         val msg:String?=null,
                         val id:String?=null)