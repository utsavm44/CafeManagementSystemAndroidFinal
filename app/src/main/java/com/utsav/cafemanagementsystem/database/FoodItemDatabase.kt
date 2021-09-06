package com.aayush.resturant_management_system.RMS.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utsav.cafemanagementsystem.dao.AddtoCartDAO
import com.utsav.cafemanagementsystem.dao.FoodItemDAO
import com.utsav.cafemanagementsystem.dao.UserDAO
import com.utsav.cafemanagementsystem.entity.FoodItem
import com.utsav.cafemanagementsystem.entity.ForAddItem
import com.utsav.cafemanagementsystem.entity.User


@Database(
        entities =[(User::class), (FoodItem::class),(ForAddItem::class)],
        version = 2,

    exportSchema = false

)

abstract class FoodItemDatabase : RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
    abstract fun getFoodItemDAO(): FoodItemDAO
    abstract fun getAddToCartDAO(): AddtoCartDAO

    companion object{
        @Volatile
        private var instance:FoodItemDatabase?= null
        fun getInstance(context: Context):FoodItemDatabase{
            if(instance==null){
                synchronized(FoodItemDatabase::class){
                    instance=buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context)=
                Room.databaseBuilder(
                        context.applicationContext,
                        FoodItemDatabase::class.java,
                        "FoodItemDB"
                ).build()
    }
}