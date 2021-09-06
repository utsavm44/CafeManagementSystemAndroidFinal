package com.utsav.cafemanagementsystem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utsav.cafemanagementsystem.dao.FoodMenuDAO
import com.utsav.cafemanagementsystem.dao.UserDAO
import com.utsav.cafemanagementsystem.entity.FoodMenu
import com.utsav.cafemanagementsystem.entity.User


@Database(
        entities =[(User::class), (FoodMenu::class)],
        version = 2
)
abstract class
FoodMenuDatabase: RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
    abstract fun getFoodMenuDAO(): FoodMenuDAO

    companion object{
        @Volatile
        private var instance: FoodMenuDatabase?= null
        fun getInstance(context: Context): FoodMenuDatabase {
            if(instance ==null){
                synchronized(FoodMenuDatabase::class){
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context)=
                Room.databaseBuilder(
                        context.applicationContext,
                        FoodMenuDatabase::class.java,
                        "FoodMenuDB"
                ).build()
    }
}