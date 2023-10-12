package com.sagrd.kevin_p1_ap2.ui.Division

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
    var nombre by mutableStateOf(" ")
    var dividendo by mutableStateOf(" ")
    var divisor by mutableStateOf(" ")
    var cociente by mutableStateOf(" ")
    var residuo by mutableStateOf(" ")

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

    fun onNombreChange(value : String){
        nombre = value
        nombreError=nombre.isNullOrBlank()
    }
    fun onDividendoChange(value : String){
        dividendo=value
        if (dividendo.isInt()) {
            divisorError =
                (dividendo.toInt() != ((divisor.toInt() * cociente.toInt()) + residuo.toInt()))
        }
    }
    fun onDivisorChange(value : String){
        divisor=value
        if (divisor.isInt()) {
            divisorError =
                (divisor.toInt() != ((dividendo.toInt() - residuo.toInt()) / cociente.toInt()))
        }
    }
    fun onCocienteChange(value : String){
        cociente= value
        if (cociente.isInt()){
            cocienteError= (cociente.toInt() != (dividendo.toInt()/divisor.toInt()))
        }
    }
    fun onResiduoChange(value : String) {
        residuo = value
        if (residuo.isInt()) {
            residuoError = (residuo.toInt() != (dividendo.toInt() % divisor.toInt()))
        }
    }
        fun AutoComplete(){
            if (residuo.isNullOrBlank()){
                residuo = "${(dividendo.toInt() % divisor.toInt())}"
            }
            if (dividendo.isNullOrBlank()){
                dividendo = "${((divisor.toInt() * cociente.toInt() ) + residuo.toInt())}"
            }
            if (divisor.isNullOrBlank()){
                divisor = "${((dividendo.toInt() - residuo.toInt()) / cociente.toInt())}"
            }
            if (cociente.isNullOrBlank()){
                cociente = "${(dividendo.toInt()/divisor.toInt())}"
            }

        }

    fun validsafe() : Boolean{
        return nombreError && dividendoError && cocienteError && divisorError && residuoError
    }

    fun delete(division: Division){
        viewModelScope.launch {
            divisionrepository.delete(division)
        }
    }

    fun clear(){
        nombre=""
        divisor=""
        dividendo=""
        cociente=""
        residuo=""
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
        }
    }

}