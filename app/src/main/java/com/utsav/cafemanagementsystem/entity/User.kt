package com.utsav.cafemanagementsystem.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey(autoGenerate = false)
        val _id: String = "",
        val name: String? = null,
        val address: String? = null,
        val dob : String? = null,
        val gender : String? =null,
        val phone : String? = null,
        val email: String? = null,
        val password: String? = null,
        val image: String? = null
)