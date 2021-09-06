package com.utsav.cafemanagementsystem.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utsav.cafemanagementsystem.entity.FoodItem


@Dao
interface FoodItemDAO {
    @Insert
    suspend fun AddProdcut(list:List<FoodItem>?)

    @Query("select * from FoodItem")
    suspend fun getproduct(): List<FoodItem>?

    @Query("Delete from FoodItem")
    suspend fun deleteFoodItem()

    @Insert
    suspend fun insertfoodItem(fooditem: MutableList<FoodItem>?)

    @Query("delete from FoodItem")
    suspend fun dropTable()
}