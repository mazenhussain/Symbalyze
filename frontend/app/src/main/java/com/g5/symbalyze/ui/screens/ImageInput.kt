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
import androidx.activity.*
import android.content.Intent
import androidx.activity.result.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.storage.FirebaseStorage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.File
import android.Manifest
import android.provider.MediaStore

@Composable
fun ImageInput(navController: NavController) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var downloadUrl by remember { mutableStateOf<String?>(null) }
    var statusMessage by remember { mutableStateOf("No image selected") }

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    val context = LocalContext.current

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                uploadToFirebase(uri, storageRef, { url -> downloadUrl = url }, { msg -> statusMessage = msg })
            } ?: run { statusMessage = "No image selected" }
        } else {
            statusMessage = "Gallery selection cancelled"
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
                uploadToFirebase(it, storageRef, { url -> downloadUrl = url }, { msg -> statusMessage = msg })
            } ?: run { statusMessage = "Camera failed to return photo" }
        } else {
            statusMessage = "Camera capture cancelled"
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(intent)
            } catch (e: Exception) {
                statusMessage = "Camera launch failed: ${e.message}"
            }
        } else {
            statusMessage = "Camera permission denied"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // header
        Text(
            "pick your method!",
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
                contentDescription = "Camera Image"
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
                contentDescription = "Gallery Image"
            )
        }
        Text(text = statusMessage, modifier = Modifier.padding(8.dp))
        downloadUrl?.let { url ->
            Text(text = "Download URL: $url", modifier = Modifier.padding(8.dp))
        }
    }
}

fun uploadToFirebase(
    uri: Uri,
    storageRef: com.google.firebase.storage.StorageReference,
    onSuccess: (String) -> Unit,
    onStatusUpdate: (String) -> Unit
) {
    onStatusUpdate("Uploading image...")
    val fileName = "images/${uri.lastPathSegment ?: "photo_${System.currentTimeMillis()}.jpg"}"
    val imageRef = storageRef.child(fileName)

    imageRef.putFile(uri)
        .addOnSuccessListener {
            onStatusUpdate("Upload successful, fetching URL...")
            imageRef.downloadUrl
                .addOnSuccessListener { url ->
                    onSuccess(url.toString())
                    onStatusUpdate("Verified URL: $url")
                    println("Download URL: $url")
                }
                .addOnFailureListener { exception ->
                    onStatusUpdate("Failed to get URL: $exception")
                    println("Failed to get URL: $exception")
                }
        }
        .addOnFailureListener { exception ->
            onStatusUpdate("Upload failed: $exception")
            println("Upload failed: $exception")
        }
}