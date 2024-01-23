package com.example.evfinalpii.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class serviciosBasicos(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val nombre:String,
    val precio:Int,
    val fecha:String
)

