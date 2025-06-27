package com.sh.work.infonumber.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.text.input.KeyboardType
import com.sh.work.infonumber.R

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel(), navController: NavController) {
    val inputNumber = viewModel.inputNumber
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val halfScreen = screenHeight / 3


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(WindowInsets.systemBars.asPaddingValues())
        .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = inputNumber,
                onValueChange = { newValue ->
                    if(newValue.all {it.isDigit()}){
                        viewModel.onInputNumberChange(newValue)
                    }
                },
                label = { Text(stringResource(R.string.label_enter_number)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {

                    inputNumber.toIntOrNull()?.let { number ->
                        viewModel.sendQuery(number) {
                            viewModel.onInputNumberChange("")
                        }
                    }
                },
                modifier = Modifier.alignByBaseline()
            ) {
                Text(stringResource(R.string.btn_get_fact))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    viewModel.sendStaticQuery()
                },
                modifier = Modifier.alignByBaseline()
            ) {
                Text(stringResource(R.string.btn_get_fact_random))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = halfScreen, bottom = 12.dp)
            ) {
                items(viewModel.histories.sortedByDescending { it.dateQuery }) { history ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable
                            {
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedHistory", history)
                                navController.navigate("details")
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "${history.number}: ${history.description}",
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}