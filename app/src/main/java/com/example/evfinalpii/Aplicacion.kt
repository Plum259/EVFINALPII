package com.example.evfinalpii

import android.app.Application
import androidx.room.Room
import com.example.evfinalpii.db.BaseDatos

class Aplicacion:Application() {
    val db by lazy {Room.databaseBuilder(this,BaseDatos::class.java,"RegistrosServicios.bd").build()}
    val serviciosDao by lazy {db.serviciosDao()}
}