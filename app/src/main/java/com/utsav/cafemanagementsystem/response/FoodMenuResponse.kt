package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.FoodMenu

data class FoodMenuResponse (
    val success: Boolean? = null,
    val data: MutableList<FoodMenu>? = null
)