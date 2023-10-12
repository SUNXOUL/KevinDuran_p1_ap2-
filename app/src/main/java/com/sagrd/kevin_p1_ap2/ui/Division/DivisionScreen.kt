package com.sagrd.kevin_p1_ap2.ui.Division

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionScreen(
    divisionViewModel: DivisionViewModel = hiltViewModel()
) {
    val divisions by divisionViewModel.Divisions.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ap2Parcial1") },
                modifier = Modifier.shadow(4.dp),
                actions = {
                    IconButton(
                        onClick = { divisionViewModel.clear() }) {
                        Icon(
                            imageVector = Icons.Outlined.CleaningServices,
                            contentDescription = "Clear"
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
                                Text(text = "Nombre es Requerido")
                            }

                        }
                        Spacer(modifier = Modifier.padding(top = 8.dp))
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
                                    Text(text = "Dividendo es Requerido")
                                }
                                if (divisionViewModel.dividendoError) {
                                    Text(text = "Dividendo es Incorrecto")
                                }
                            }
                            Spacer(modifier = Modifier.padding(start = 8.dp))
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
                                    Text(text = "No se puede dividir entre 0")
                                }
                                if (divisionViewModel.divisorError) {
                                    Text(text = "Divisor es Incorrecto")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 8.dp))
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
                                    Text(text = "Cociente es Requerido")
                                }
                                if (divisionViewModel.cocienteError) {
                                    Text(text = "Cociente es Incorrecto")
                                }
                            }
                            Spacer(modifier = Modifier.padding(start = 8.dp))
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
                                    Text(text = "Residuo es Requerido")
                                }
                                if (divisionViewModel.residuoError) {
                                    Text(text = "Residuo es Incorrecto")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            OutlinedButton(onClick = { divisionViewModel.save() })
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
                    Spacer(modifier = Modifier.padding(top = 12.dp))
                    Row {
                        Text(text = "Historial de resultados")
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = "History"
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(divisions)
                        { division ->
                            OutlinedCard(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                            )
                            {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                )
                                {
                                    Text(
                                                    text = """
                                        NOMBRE          : ${division.nombre}
                                        DIVIDENDO       : ${division.dividendo}
                                        DIVISOR         : ${division.divisor}
                                        COCIENTE        : ${division.cociente}
                                        RESIDUO         : ${division.residuo}
                                    """.trimIndent(), modifier = Modifier.padding(8.dp)
                                    )

                                    Spacer(modifier = Modifier.padding(start = 100.dp))
                                    IconButton(onClick = { divisionViewModel.delete(division) }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "Delete"
                                        )
                                    }
                                }
                                Divider()
                            }

                        }

                }

            }
        })
    )
}