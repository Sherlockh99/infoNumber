package com.sh.work.infonumber.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val dateFormat = rememberDateFormat()
    var inputNumber = viewModel.inputNumber

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = inputNumber,
                onValueChange = { viewModel.onInputNumberChange(it)},
                label = { Text("Введите число") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {

                    inputNumber.toIntOrNull()?.let { number ->
                        viewModel.sendQuery(number){
                            viewModel.onInputNumberChange("")
                        }
                    }
                },
                modifier = Modifier.alignByBaseline()
            ) {
                Text("Запросить")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.histories) { history ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Номер: ${history.number}", style = MaterialTheme.typography.titleMedium)
                        Text("Описание: ${history.description}", style = MaterialTheme.typography.bodyMedium)
                        Text("Дата: ${dateFormat.format(history.dateQuery)}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}


@Composable
fun rememberDateFormat(): SimpleDateFormat {
    return remember {
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    }
}
