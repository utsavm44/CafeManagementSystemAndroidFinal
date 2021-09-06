package com.utsav.cafemanagementsystem.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.utsav.cafemanagementsystem.entity.FoodMenu

@Dao
interface FoodMenuDAO {
   @Query("Delete from FoodMenu")
    suspend fun deleteFoodMenu()

    @Insert
    suspend fun insertfoodmenu(foodmenu: MutableList<FoodMenu>?)
}