package com.g5.util

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

object FirebaseLoader {
    fun initFirebase() {
        if (FirebaseApp.getApps().isEmpty()) {
            val serviceAccount = FileInputStream("src/main/resources/firebase-admin.json")
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            FirebaseApp.initializeApp(options)
            println("success: Firebase initialized!")
        } else {
            println("warning: Firebase already initialized!")
        }
    }
}
