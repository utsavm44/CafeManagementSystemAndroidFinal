package com.utsav.cafemanagementsystem.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
  class FoodMenu (
    @PrimaryKey(autoGenerate = false)
         val _id : String = "",
         val menu_name : String? = null,
         val menu_title : String? = null,
         val menu_price : String? = null,
         val menu_desc : String? = null,
         val menu_image : String? = null
 )