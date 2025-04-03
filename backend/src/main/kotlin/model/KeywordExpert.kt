package com.g5.model

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
        val result: Symbol? = bestSymbolKeywordMatch(input, symbols)
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
        val inputAdjusted: List<String> = input.lowercase().split(" ", "-", "_", ",")
//        symbols.forEach { symbol ->
//            val score = symbol.tags.count { tag ->
//                inputAdjusted.any { word -> tag.lowercase().contains(word) }
//            }
//            println("🔍 Symbol: ${symbol.name}, Score: $score")
//        }
        return symbols.maxByOrNull { symbol ->
            symbol.tags.sumOf { tag ->
                tag.lowercase().split(" ", "-", "_", ",")
                    .filter{it.isNotBlank() && it !in
                        setOf("a", "an", "and", "the", "of", "on",
                        "in", "at", "for", "with", "to", "from", "by")}
                    .count { subtag ->
                        inputAdjusted.any { word -> subtag == word }
                    }
            }
        }

    }

}