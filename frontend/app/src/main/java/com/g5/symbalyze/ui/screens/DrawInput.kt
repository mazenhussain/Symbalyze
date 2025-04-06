package com.g5.symbalyze.ui.screens

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.g5.symbalyze.api.identifySymbol
import convertCanvasToBase64
import kotlinx.coroutines.launch

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.Black,
    val strokeWidth: Dp = 5.dp
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawInputScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DrawInput()
        }
    }
}

@Composable
fun DrawInput() {
    val lines = remember { mutableStateListOf<Line>() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "ready? draw below!",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 48.dp)
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
                Text("clear", fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val base64 = convertCanvasToBase64(lines).getOrElse { "" }
                        Log.d("debug", "base64: $base64")
                        val res = identifySymbol(inputImgBase64 = base64)
                        Log.d("debug", res.toString())
                        lines.clear()
                        // TODO: navController.navigate("result") with the response body somehow
                    }
                },
                modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                enabled = lines.isNotEmpty()
            ) {
                Text("submit", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}