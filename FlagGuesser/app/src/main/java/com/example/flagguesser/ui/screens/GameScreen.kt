package com.example.flagguesser.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.flagguesser.data.*
import com.example.flagguesser.ui.components.*
import com.example.flagguesser.ui.theme.*

@Composable
fun GameScreen(
    gameState: GameState,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onExit: () -> Unit
) {

    val region = gameState.currentRegion
    val totalFlags = region?.totalFlags ?: 1

    BaseScreen {

        Column(modifier = Modifier.fillMaxSize()) {

            TopPanel(
                title = "${gameState.currentIndex + 1}/$totalFlags\n${region?.name ?: ""}",
                showBack = true,
                showNext = true,
                onBack = onPrev,
                onNext = onNext
            )

            Spacer(Modifier.height(130.dp))

            // TODO: добавить изображение флага
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(206.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(20.dp))
                    .border(4.dp, StrokeColor, RoundedCornerShape(20.dp))
            )

            Spacer(Modifier.height(95.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                Row {
                    MainButton("A", Modifier.weight(1f).height(100.dp))
                    Spacer(Modifier.width(26.dp))
                    MainButton("B", Modifier.weight(1f).height(100.dp))
                }

                Spacer(Modifier.height(40.dp))

                Row {
                    MainButton("C", Modifier.weight(1f).height(100.dp))
                    Spacer(Modifier.width(26.dp))
                    MainButton("D", Modifier.weight(1f).height(100.dp))
                }
            }

            Spacer(Modifier.height(50.dp))

            MainButton(
                "< В меню",
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                onExit
            )
        }
    }
}