package com.utsav.cafemanagementsystem.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForAddItem   (
    @PrimaryKey( autoGenerate = true) val primaryKey:Int=0,
    val _id : String? = null,
    val food_name: String? = null,
    val food_price: String? = null,
    val food_desc: String? = null,
    val food_image: String? = null
)
{

}