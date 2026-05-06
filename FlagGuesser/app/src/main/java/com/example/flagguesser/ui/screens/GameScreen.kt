package com.example.flagguesser.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.flagguesser.data.GameState
import com.example.flagguesser.ui.components.BaseScreen
import com.example.flagguesser.ui.components.MainButton
import com.example.flagguesser.ui.components.TopPanel
import com.example.flagguesser.ui.theme.ProgressColor
import com.example.flagguesser.ui.theme.StrokeColor
import androidx.compose.ui.res.stringResource
import com.example.flagguesser.R

@Composable
fun GameScreen(
    gameState: GameState,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onExit: () -> Unit,
    onAnswer: (String) -> Unit
) {
    val region = gameState.currentRegion
    val totalFlags = region?.totalFlags ?: 1
    val currentCountry = region?.countries?.getOrNull(gameState.currentIndex)
    val correctAnswer = currentCountry?.name ?: ""

    BaseScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TopPanel(
                title = "${gameState.currentIndex + 1}/$totalFlags\n${region?.name ?: ""}",
                showBack = true,
                showNext = true,
                onBack = onPrev,
                onNext = onNext
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(206.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(4.dp, StrokeColor, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (currentCountry != null && currentCountry.flagUrl.isNotBlank()) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = currentCountry.flagUrl,
                                error = androidx.compose.ui.res.painterResource(com.example.flagguesser.R.drawable.ic_launcher_foreground)
                            ),
                            contentDescription = "Flag of ${currentCountry.name}",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text(stringResource(R.string.label_flag_not_loaded), color = MaterialTheme.colorScheme.onSurface)
                    }
                }

                Spacer(Modifier.height(32.dp))

                if (gameState.options.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        gameState.options.chunked(2).forEach { rowOptions ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(26.dp)
                            ) {
                                rowOptions.forEach { option ->
                                    val buttonColor = when {
                                        !gameState.isAnswered -> MaterialTheme.colorScheme.primary
                                        option == correctAnswer -> ProgressColor
                                        gameState.isAnswered && option == gameState.selectedAnswer && option != correctAnswer -> Color.Red
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    }
                                    Button(
                                        onClick = { if (!gameState.isAnswered) onAnswer(option) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(80.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                                    ) {
                                        Text(option, fontSize = 18.sp, maxLines = 2)
                                    }
                                }
                                if (rowOptions.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(Modifier.height(24.dp))
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }

            MainButton(
                stringResource(R.string.btn_back_to_regions),
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                onExit
            )
        }
    }
}