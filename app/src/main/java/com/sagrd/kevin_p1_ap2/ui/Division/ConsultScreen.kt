package com.sagrd.kevin_p1_ap2.ui.Division

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultDivisionScreen(
    divisionViewModel: DivisionViewModel = hiltViewModel()
) {
    val divisions by divisionViewModel.Divisions.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        divisionViewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Division Eliminada",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
Row {
    Text(text = "Historial de Divisiones")
    Icon(imageVector = Icons.Filled.AccessTime, contentDescription = "Time")
}
    Divider()
LazyColumn(
modifier = Modifier
    .fillMaxSize()
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)) {
                    Text(text = division.nombre)
                    Divider()
                    Row {
                        Text(
                            text = "Dividendo : ${division.dividendo}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Text(
                            text = "Divisor : ${division.divisor}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                    Row {
                        Text(
                            text = "Cociente : ${division.cociente}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Text(
                            text = "Residuo : ${division.residuo}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }

                IconButton(
                    onClick = {
                    divisionViewModel.delete(division)
                    divisionViewModel.setMessageShown()
                },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f))
                {
                    OutlinedCard {
                        Text(text = "X",modifier=Modifier.padding(8.dp), color = Color.Red)
                    }

                }
            }

        }

    }

    }
}