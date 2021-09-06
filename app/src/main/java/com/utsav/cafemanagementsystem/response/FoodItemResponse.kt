package com.utsav.cafemanagementsystem.response

import com.utsav.cafemanagementsystem.entity.FoodItem

data class FoodItemResponse (
        val success : Boolean? = null,
        val data : MutableList<FoodItem>? = null
)