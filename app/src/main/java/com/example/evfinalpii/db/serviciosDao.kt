package com.example.evfinalpii.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface serviciosDao {
    @Query("SELECT count(*) FROM serviciosBasicos")
    suspend fun contar(): Int

    @Query("SELECT * FROM serviciosBasicos ORDER BY fecha DESC")
    suspend fun obtenerRegistro(): List<serviciosBasicos>

    @Insert
    suspend fun insertar(serviciosBasicos: serviciosBasicos)

    @Delete
    suspend fun borrar(serviciosBasicos: serviciosBasicos)

    @Update
    suspend fun actualizar(vararg serviciosBasicos: serviciosBasicos)
}