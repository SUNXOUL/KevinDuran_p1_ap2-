package com.sagrd.kevin_p1_ap2.data.repository

import com.sagrd.kevin_p1_ap2.data.local.dao.DivisionDao
import com.sagrd.kevin_p1_ap2.data.local.entity.Division
import javax.inject.Inject

class DivisionRepository @Inject constructor(
    private val divisionDao : DivisionDao
){
    suspend fun save(division : Division) = divisionDao.save(division)
    suspend fun delete(division: Division)= divisionDao.delete(division)
    fun getAll()=divisionDao.getAll()
}