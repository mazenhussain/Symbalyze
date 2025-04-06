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
import com.g5.symbalyze.model.SymbolResponse
import java.util.UUID

var currentResponse: SymbolResponse? = null
//var currentResponse = SymbolResponse(UUID.fromString("00000000-0000-0000-0000-000000000000"), "empty", "empty")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsDisplayScreen(navController: NavController) {
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
            ResultsDisplay()
        }
    }
}

fun ResultUpdate(response: SymbolResponse?) {
    currentResponse = response
}

@Composable
fun ResultsDisplay() {
    val lines = remember { mutableStateListOf<Line>() }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "here is what our 3 experts found!",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 48.dp)
        )
        currentResponse?.let {
            Text(
                text = it.name,
                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                ), modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 48.dp)
            )
        }
        currentResponse?.let {
            Text(
                text = it.context,
                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                ), modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 48.dp)
            )
        }
    }
}