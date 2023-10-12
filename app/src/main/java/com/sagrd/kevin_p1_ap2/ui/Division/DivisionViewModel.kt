package com.sagrd.kevin_p1_ap2.ui.Division

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagrd.kevin_p1_ap2.data.local.entity.Division
import com.sagrd.kevin_p1_ap2.data.repository.DivisionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DivisionViewModel @Inject constructor(
    private val divisionrepository: DivisionRepository
) : ViewModel() {
    var nombre by mutableStateOf("")
    var dividendo by mutableStateOf("0")
    var divisor by mutableStateOf("0")
    var cociente by mutableStateOf("0")
    var residuo by mutableStateOf("0")

    var nombreError by mutableStateOf(true)
    var dividendoError by mutableStateOf(true)
    var divisorError by mutableStateOf(true)
    var cocienteError by mutableStateOf(true)
    var residuoError by mutableStateOf(false)

    val Divisions : StateFlow<List<Division>> = divisionrepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun String.isInt(): Boolean{
        try {
            this.toInt()
            return true
        }
        catch (e: Exception){
            return false
        }
    }
    fun String.isZero(): Boolean{
        try {
            return this.toInt() == 0
        }
        catch (e: Exception){
            return false
        }
    }

    fun onNombreChange(value : String){
        nombre = value
        nombreError=nombre.isNullOrBlank()
    }
    fun onDividendoChange(value : String){
        dividendo=value
        checkValues()
    }
    @SuppressLint("SuspiciousIndentation")
    fun onDivisorChange(value : String){
        divisor=value
        checkValues()
    }
    fun onCocienteChange(value : String){
        cociente= value
        checkValues()
    }
    fun onResiduoChange(value : String) {
        residuo = value
        checkValues()
    }
    fun checkResiduo(){
        if (!dividendo.isZero() && !divisor.isZero() && cociente.isInt() && residuo.isInt()) {
            residuoError = (residuo.toInt() != (dividendo.toInt() % divisor.toInt()))
        }
    }
    fun checkCociente(){
        if (!dividendo.isZero() && !divisor.isZero() && cociente.isInt() && residuo.isInt()){
            cocienteError= (cociente.toInt() != (dividendo.toInt()/divisor.toInt()))
        }
    }
    fun checkDivisor(){
        if (dividendo.isInt() && divisor.isInt() && !cociente.isZero() && residuo.isInt()) {
            divisorError = (divisor.toInt() != ((dividendo.toInt() - residuo.toInt()) / cociente.toInt()))
        }
    }
    fun checkDividendo(){
        if (dividendo.isInt() && !divisor.isZero() && !cociente.isZero() && residuo.isInt()) {
            dividendoError = (dividendo.toInt() != ((divisor.toInt() * cociente.toInt()) + residuo.toInt()))
        }
    }
    fun autoComplete(){
        if (cociente.toInt()==0){
           onCocienteChange("${(dividendo.toInt()/divisor.toInt())}")
        }
        if (residuo.toInt()==0){
            onResiduoChange("${(dividendo.toInt() % divisor.toInt())}")
        }
        if (dividendo.toInt()==0){
            onDividendoChange("${((divisor.toInt() * cociente.toInt() ) + residuo.toInt())}")
        }
        if (divisor.toInt()==0) {
            onDivisorChange("${((dividendo.toInt() - residuo.toInt()) / cociente.toInt())}")
        }
        checkValues()
    }

    fun checkValues(){
        checkResiduo()
        checkDivisor()
        checkCociente()
        checkDividendo()

    }

    fun validsafe() : Boolean{
        return !nombreError && !dividendoError && !cocienteError && !divisorError && !residuoError
    }

    fun delete(division: Division){
        viewModelScope.launch {
            divisionrepository.delete(division)
        }
    }

    fun clear(){
        onNombreChange(" ")
        onDivisorChange("0")
        onDividendoChange("0")
        onCocienteChange("0")
        onResiduoChange("0")
        checkValues()

    }

    fun save(){
        viewModelScope.launch {
            if(validsafe()){
                divisionrepository.save(Division(
                    nombre= nombre,
                    divisor = divisor.toInt(),
                    dividendo = dividendo.toInt(),
                    cociente = cociente.toInt(),
                    residuo = residuo.toInt()
                ))
            }
            clear()
        }
    }

}