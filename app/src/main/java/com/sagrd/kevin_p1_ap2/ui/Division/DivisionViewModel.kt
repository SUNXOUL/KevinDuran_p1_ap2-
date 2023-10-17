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
    var dividendo by mutableStateOf("")
    var divisor by mutableStateOf("")
    var cociente by mutableStateOf("")
    var residuo by mutableStateOf("")

    var nombreError by mutableStateOf(true)
    var dividendoError by mutableStateOf(true)
    var divisorError by mutableStateOf(true)
    var cocienteError by mutableStateOf(true)
    var residuoError by mutableStateOf(true)

    val Divisions : StateFlow<List<Division>> = divisionrepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun String.isInt(): Boolean{
        try {
            if (this.isEmpty()){
                return false
            }
            val prueba = this.toInt()
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
        if (!dividendo.isZero() && !divisor.isZero() && cociente.isInt() && residuo.isInt() && divisor.isInt() && dividendo.isInt()) {
            residuoError = (residuo.toInt() != (dividendo.toInt() % divisor.toInt()))
        }
        else
        {
            residuoError=true
        }
    }
    fun checkCociente(){
        if (!dividendo.isZero() && !divisor.isZero() && cociente.isInt() && residuo.isInt() && divisor.isInt() && dividendo.isInt()){
            cocienteError= (cociente.toInt() != (dividendo.toInt()/divisor.toInt()))
        }
        else
        {
            cocienteError=true
        }
    }
    fun checkDivisor(){
        if (dividendo.isInt() && divisor.isInt() && !cociente.isZero() && cociente.isInt() && residuo.isInt()) {
            divisorError = (divisor.toInt() != ((dividendo.toInt() - residuo.toInt()) / cociente.toInt()))
        }
        else
        {
            divisorError=true
        }
    }
    fun checkDividendo(){
        if (dividendo.isInt() && !divisor.isZero() && !cociente.isZero() && divisor.isInt() && cociente.isInt()&& residuo.isInt()) {
            dividendoError = (dividendo.toInt() != ((divisor.toInt() * cociente.toInt()) + residuo.toInt()))
        }
        else
        {
            dividendoError=true
        }
    }
    fun autoComplete(){
        if (cociente.isNullOrBlank() and dividendo.isInt() and divisor.isInt()){
           onCocienteChange("${(dividendo.toInt()/divisor.toInt())}")
        }
        if (residuo.isNullOrBlank() and dividendo.isInt() and divisor.isInt()){
            onResiduoChange("${(dividendo.toInt() % divisor.toInt())}")
        }
        if (dividendo.isNullOrEmpty() and divisor.isInt() and cociente.isInt() and residuo.isInt()){
            onDividendoChange("${((divisor.toInt() * cociente.toInt() ) + residuo.toInt())}")
        }
        if (divisor.isNullOrEmpty() and dividendo.isInt() and cociente.isInt() and residuo.isInt()) {
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
        onNombreChange("")
        onDivisorChange("")
        onDividendoChange("")
        onCocienteChange("")
        onResiduoChange("")
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
                clear()
                checkValues()
            }

        }
    }

}