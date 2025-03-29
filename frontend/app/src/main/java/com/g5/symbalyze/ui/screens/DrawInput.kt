package com.g5.symbalyze.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Line(
    val start: Offset, val end: Offset, val color: Color = Color.Black, val strokeWidth: Dp = 5.dp
)

@Composable
fun DrawInput() {
    val lines = remember { mutableStateListOf<Line>() }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "draw the symbol!", style = TextStyle(
                fontSize = 32.sp, color = Color.Black, fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 100.dp)
        )

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val line = Line(
                        start = change.position - dragAmount, end = change.position
                    )
                    lines.add(line)
                }
            }) {
            lines.forEach { line ->
                drawLine(
                    color = line.color,
                    start = line.start,
                    end = line.end,
                    strokeWidth = line.strokeWidth.toPx()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    lines.clear()
                },
                modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                BasicText(
                    "clear",
                    style = TextStyle(color = Color.White, fontWeight = FontWeight.SemiBold)
                )
            }

            Button(
                onClick = {
                    println("Submitted drawing: $lines") // TODO - connect to backend
                },
                modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                BasicText(
                    "submit",
                    style = TextStyle(color = Color.White, fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}