package com.example.flagguesser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flagguesser.ui.components.BaseScreen
import com.example.flagguesser.ui.components.MainButton
import com.example.flagguesser.ui.components.TopPanel
import com.example.flagguesser.ui.viewmodel.StatsViewModel

@Composable
fun StatsScreen(onBack: () -> Unit) {
    val statsViewModel: StatsViewModel = viewModel()
    val stats by statsViewModel.totalStats.collectAsState()

    BaseScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            TopPanel("СТАТИСТИКА", showBack = true, onBack = onBack)

            Spacer(Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Всего угадано флагов:", style = MaterialTheme.typography.bodyLarge)
                        Text("${stats.first}", style = MaterialTheme.typography.displayLarge)
                        Spacer(Modifier.height(16.dp))
                        Text("Всего попыток ответа:", style = MaterialTheme.typography.bodyLarge)
                        Text("${stats.second}", style = MaterialTheme.typography.displayLarge)
                        Spacer(Modifier.height(16.dp))
                        val accuracy = if (stats.second > 0) (stats.first.toFloat() / stats.second * 100).toInt() else 0
                        Text("Точность: $accuracy%", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(Modifier.height(35.dp))

                MainButton("Сбросить статистику", Modifier.fillMaxWidth()) {
                    statsViewModel.resetStats()
                }
            }
        }
    }
}