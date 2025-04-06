package com.g5.symbalyze.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.g5.symbalyze.api.identifySymbol
import com.g5.symbalyze.ui.shared.GlobalState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeInputScreen(navController: NavController) {
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
            TypeInput(navController)
        }
    }
}

@Composable
fun TypeInput(navController: NavController) {
    var userInput by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val maxlen = 200

    Text(
        text = "describe the symbol...",
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(bottom = 12.dp)
            .padding(horizontal = 24.dp)
    )

    androidx.compose.material3.OutlinedTextField(
        value = userInput,
        onValueChange = {
            if (it.length <= maxlen) userInput = it
        },
        placeholder = {
            Text("e.g. Golden arches forming the letter M", fontSize = 16.sp)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .padding(horizontal = 24.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "${userInput.length} / $maxlen",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    Log.d("debug", userInput)
                    val res = identifySymbol(inputDesc = userInput)
                    Log.d("debug", res.toString())
                    userInput = ""

                    GlobalState.symbolResponse = res
                    navController.navigate("result")
                }
            },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            enabled = userInput.isNotBlank()
        ) {
            Text("submit", fontWeight = FontWeight.SemiBold)
        }
    }
}

