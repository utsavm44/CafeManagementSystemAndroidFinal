package com.utsav.cafemanagementsystem.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Table  (
    @PrimaryKey(autoGenerate = false)
    val _id: String = "",
    val user_email: String? = null,
    val people: String? = null,
    val date : String? = null,
    val time : String? =null

)
