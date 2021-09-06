package com.utsav.cafemanagementsystem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utsav.cafemanagementsystem.entity.ForAddItem

@Dao
interface AddtoCartDAO {
    @Insert
    suspend fun AddCart(list:List<ForAddItem>?)

    @Query("select * from ForAddItem")
    suspend fun getCart(): List<ForAddItem>?

    @Query("delete from ForAddItem")
    suspend fun dropTable()
}