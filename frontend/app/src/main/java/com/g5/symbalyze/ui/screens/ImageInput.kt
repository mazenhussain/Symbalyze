package com.g5.symbalyze.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.*
import androidx.compose.foundation.Image
import com.g5.symbalyze.R
import android.content.Intent
import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.File
import android.provider.MediaStore
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.rememberCoroutineScope
import com.g5.symbalyze.api.identifySymbol
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import android.util.Base64
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.asImageBitmap
import convertUriToBase64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageInputScreen(navController: NavController) {
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
            ImageInput(navController)
        }
    }
}
@Composable
fun ImageInput(navController: NavController) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var downloadUrl by remember { mutableStateOf<String?>(null) }
    var statusMessage by remember { mutableStateOf("no image selected") }
    var base64Image by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                base64Image = convertUriToBase64(context, uri)
                statusMessage = "gallery image selected"
            } ?: run { statusMessage = "no image selected" }
        } else {
            statusMessage = "gallery selection cancelled"
        }
    }

    // Camera launcher (no FileProvider)
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            // Get the bitmap thumbnail or Uri from the camera
            val uri = result.data?.data ?: run {
                // If no Uri, try to get from extras (thumbnail)
                val bitmap = result.data?.extras?.get("data") as? android.graphics.Bitmap
                if (bitmap != null) {
                    // Save bitmap to a file and get Uri
                    val file = File(context.cacheDir, "camera_${System.currentTimeMillis()}.jpg")
                    file.outputStream().use { bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, it) }
                    Uri.fromFile(file)
                } else {
                    null
                }
            }
            uri?.let {
                selectedImageUri = it
                base64Image = convertUriToBase64(context, uri)
                statusMessage = "camera photo selected"
            } ?: run { statusMessage = "camera failed to return photo" }
        } else {
            statusMessage = "camera capture cancelled"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // header
        Text(
            "take or upload a picture!",
            fontSize = 28.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // list of all possible input options
        Button(
            onClick = {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intent)
            },
            modifier = Modifier.width(150.dp).padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "Camera Image",
                modifier = Modifier.size(40.dp)
            )
        }
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                galleryLauncher.launch(intent)
            },
            modifier = Modifier.width(150.dp).padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.gallery),
                contentDescription = "Gallery Image",
                modifier = Modifier.size(40.dp)
            )
        }
        selectedImageUri?.let { uri ->
            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
            } ?: Text("Failed to load image", modifier = Modifier.padding(8.dp))
        }
        Text(text = statusMessage, modifier = Modifier.padding(8.dp))
        downloadUrl?.let { url ->
            Text(text = "Download URL: $url", modifier = Modifier.padding(8.dp))
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    Log.d("debug", "base64: $base64Image")
                    val resp = identifySymbol(inputImgBase64 = base64Image)
                    Log.d("debug", resp.toString())
                    ResultUpdate(resp)
                }
                navController.navigate("result")
            },
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            enabled = !base64Image.isNullOrBlank()
        ) {
            Text("submit", fontWeight = FontWeight.SemiBold)
        }
    }
}