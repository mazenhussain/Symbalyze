package com.g5.service

import com.g5.model.ExpertInterface
import com.g5.model.Symbol
import com.g5.service.GeminiService
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream


class KeywordExpert : ExpertInterface {

    override suspend fun generateResponse(input: String, isImage: Boolean?): String? {
        initFirebase()
        val symbols = getStoredSymbols()
        val description = if(isImage == false) input
            else GeminiService().askGemini("Use five word visual description of the image in the link: ", input)
        println(description)
        val result: Symbol? = bestSymbolKeywordMatch(description, symbols)
        println("keyword expert generated: " + result?.name)
        return result?.name
    }

    private fun initFirebase() {
        if (FirebaseApp.getApps().isEmpty()) {
            val serviceAccount = FileInputStream("src/main/resources/firebase-admin.json")
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            FirebaseApp.initializeApp(options)
            println("✅ Firebase initialized.")
        } else {
            println("⚠️ Firebase already initialized.")
        }
    }

    private fun getStoredSymbols(): List<Symbol> {
        val database: Firestore = FirestoreClient.getFirestore()
        val symbolInfo = database.collection("symbols").get().get()
        val symbols = symbolInfo.documents.mapNotNull { it.toObject(Symbol::class.java) }
        return symbols
    }

    private fun bestSymbolKeywordMatch(input: String, symbols: List<Symbol>) : Symbol? {
        return symbols.maxByOrNull { symbol ->
            symbol.tags.sumOf { tag ->
                tag.lowercase().split(" ", "-", "_", ",")
                    .filter{it.isNotBlank() && it !in
                        setOf("a", "an", "and", "the", "of", "on",
                        "in", "at", "for", "with", "to", "from", "by")}
                    .count { subtag ->
                        input.lowercase().split(" ", "-", "_", ",")
                            .any { word -> subtag == word }
                    }
            }
        }
    }

}