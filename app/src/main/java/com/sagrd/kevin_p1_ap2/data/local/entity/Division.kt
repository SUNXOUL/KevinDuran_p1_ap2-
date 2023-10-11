package com.sagrd.kevin_p1_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Divisiones")
data class Division (
    @PrimaryKey
    val divisionid : Int? = null,
    val dividendo : Int,
    val divisor : Int,
    val cociente : Int,
    val residuo : Int
)