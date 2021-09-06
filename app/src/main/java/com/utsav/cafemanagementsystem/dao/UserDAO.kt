package com.utsav.cafemanagementsystem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utsav.cafemanagementsystem.entity.User


//room db
@Dao
interface UserDAO {
    @Insert
    suspend fun RegisterActivity(user: User)

//    @Query("select * from User where email=(:email) and password=(:password)")
//    suspend fun checkUser(email: String, password:String)

    @Query("Delete from User")
    suspend fun logout()
}