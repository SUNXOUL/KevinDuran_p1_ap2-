package com.sagrd.kevin_p1_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Divisiones")
data class Division (
    @PrimaryKey
    val divisionId : Int? = null,
    val nombre : String,
    val dividendo : Int,
    val divisor : Int,
    val cociente : Int,
    val residuo : Int
)