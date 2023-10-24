package com.sagrd.kevin_p1_ap2.ui.Division

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sagrd.kevin_p1_ap2.util.nav.AppScreens
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionScreen(
    divisionViewModel: DivisionViewModel = hiltViewModel(),
    navController : NavController,
    context: Context
) {
    val divisions by divisionViewModel.Divisions.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        divisionViewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Division Agregada",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Ap2Parcial1") },
                modifier = Modifier.shadow(4.dp),
                actions = {
                    IconButton(
                        onClick = { navController.navigate(route = AppScreens.ConsultScreen.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = "History"
                        )
                    }
                    IconButton(
                        onClick = { divisionViewModel.clear() }) {
                        Icon(
                            imageVector = Icons.Outlined.CleaningServices,
                            contentDescription = "Clear"
                        )
                    }
                    IconButton(
                        onClick = { divisionViewModel.autoComplete() }) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = "AutoComplete"
                        )
                    }
                })
        },
        content = ({

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
            {
                Spacer(modifier = Modifier.padding(top = 80.dp))
                Column(modifier = Modifier.padding(8.dp))
                {
                        //Nombres
                        Column {
                            OutlinedTextField(
                                value = divisionViewModel.nombre,
                                label = { Text(text = "Nombres") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                maxLines = 1,
                                isError = divisionViewModel.nombreError,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                ),
                                onValueChange = { divisionViewModel.onNombreChange(it) })
                            if (divisionViewModel.nombreError) {
                                Text(text = "Nombre es Requerido",color= Color.Red)
                            }

                        }
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        // Divisor y Dividendo
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = divisionViewModel.dividendo,
                                    label = { Text(text = "Dividendo") },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    maxLines = 1,
                                    isError = divisionViewModel.dividendoError,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number
                                    ),
                                    onValueChange = { divisionViewModel.onDividendoChange(it) })
                                if (divisionViewModel.dividendo.isBlank()) {
                                    Text(text = "Dividendo es Requerido",color= Color.Red)
                                }
                                if (divisionViewModel.dividendoError) {
                                    Text(text = "Dividendo es Incorrecto",color= Color.Red)
                                }
                            }
                            Spacer(modifier = Modifier.padding(start = 16.dp))
                            Column(modifier = Modifier.weight(1f))
                            {
                                OutlinedTextField(
                                    value = divisionViewModel.divisor,
                                    label = { Text(text = "Divisor") },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    maxLines = 1,
                                    isError = divisionViewModel.divisorError,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number
                                    ),
                                    onValueChange = { divisionViewModel.onDivisorChange(it) })
                                if (divisionViewModel.divisor =="0") {
                                    Text(text = "No se puede dividir entre 0",color= Color.Red)
                                }
                                if (divisionViewModel.divisorError) {
                                    Text(text = "Divisor es Incorrecto",color= Color.Red)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        // Cociente y Residuo
                        Row(modifier = Modifier.fillMaxWidth())
                        {
                            Column(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = divisionViewModel.cociente,
                                    label = { Text(text = "Cociente") },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    maxLines = 1,
                                    isError = divisionViewModel.cocienteError,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number
                                    ),
                                    onValueChange = { divisionViewModel.onCocienteChange(it) })
                                if (divisionViewModel.cociente.isBlank()) {
                                    Text(text = "Cociente es Requerido",color= Color.Red)
                                }
                                if (divisionViewModel.cocienteError) {
                                    Text(text = "Cociente es Incorrecto",color= Color.Red)
                                }
                            }
                            Spacer(modifier = Modifier.padding(start = 16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = divisionViewModel.residuo,
                                    label = { Text(text = "Residuo") },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    maxLines = 1,
                                    isError = divisionViewModel.residuoError,
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Number
                                    ),
                                    keyboardActions = KeyboardActions(onDone = { divisionViewModel.autoComplete() }),
                                    onValueChange = { divisionViewModel.onResiduoChange(it) })
                                if (divisionViewModel.residuo.isBlank()) {
                                    Text(text = "Residuo es Requerido",color= Color.Red)
                                }
                                if (divisionViewModel.residuoError) {
                                    Text(text = "Residuo es Incorrecto",color= Color.Red)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            OutlinedButton(onClick = {
                                divisionViewModel.save()
                                divisionViewModel.setMessageShown()
                            })
                            {
                                Row {
                                    Icon(
                                        imageVector = Icons.Outlined.Save,
                                        contentDescription = "Guardar"
                                    )
                                    Spacer(modifier = Modifier.padding(start = 4.dp))
                                    Text(text = "GUARDAR")
                                }

                            }
                        }
                    }
            }
        })
    )
}