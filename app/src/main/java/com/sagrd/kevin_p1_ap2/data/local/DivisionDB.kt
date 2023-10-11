package com.sagrd.kevin_p1_ap2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sagrd.kevin_p1_ap2.data.local.dao.DivisionDao
import com.sagrd.kevin_p1_ap2.data.local.entity.Division

@Database(
    entities = [Division::class],
    version = 1,
    exportSchema = false
)
abstract class DivisionDB : RoomDatabase(){
    abstract fun divisionDao() : DivisionDao
}