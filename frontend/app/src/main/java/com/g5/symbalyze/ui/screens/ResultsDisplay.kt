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

@Composable
fun ResultsDisplay() {
    val lines = remember { mutableStateListOf<Line>() }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "here is what we found!",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 48.dp)
        )

        Text(
            text = "Keyword Matching Expert: ",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 48.dp)
        )
        Text(
            text = "ML Expert:",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 48.dp)
        )
        Text(
            text = "Web-scraping Expert: ",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            ), modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = 48.dp)
        )
    }
}