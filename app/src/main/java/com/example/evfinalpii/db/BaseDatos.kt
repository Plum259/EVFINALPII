package com.example.evfinalpii.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [serviciosBasicos::class], version = 1)
abstract class BaseDatos: RoomDatabase(){
    abstract fun serviciosDao(): serviciosDao

    companion object{
        @Volatile
        private var BASE_DATOS: BaseDatos? = null
        const val BD_NOMBRE = "RegistrosServicios.bd"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(contexto: Context):BaseDatos{
            return BASE_DATOS ?: synchronized(this){
                Room.databaseBuilder(
                    contexto.applicationContext,
                    BaseDatos::class.java,
                    BD_NOMBRE
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { BASE_DATOS = it }
            }
        }
    }
}
