package com.utsav.cafemanagementsystem.database

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
    entities = [(User::class), (FoodItem::class),(ForAddItem::class)],
    version = 2,
    exportSchema = false
)
abstract class Db: RoomDatabase() {
    abstract fun getUserDao(): UserDAO
    abstract fun getFoodItemDAO(): FoodItemDAO
    abstract fun getAddToCartDAO(): AddtoCartDAO
    companion object{
        @Volatile
        private var instance: Db?=null
        fun getInstance(context: Context): Db {
            if(instance ==null){
                synchronized(Db::class){
                    instance = builderDatabase(context)
                }
            }
            return instance!!
        }

        private fun builderDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            Db::class.java,
            "UserDatabse"
        ).build()
    }
}
