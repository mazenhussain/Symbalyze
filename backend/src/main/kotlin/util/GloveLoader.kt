package com.g5.util

import java.io.File

object GloveLoader {
    private val gloveMap: MutableMap<String, List<Double>> = mutableMapOf()
    private const val GLOVE_FILE_PATH = "src/main/resources/largefiles/glove.6B.50d.txt"

    fun loadGloveModel() {
        File(GLOVE_FILE_PATH).useLines { lines ->
            lines.forEach { line ->
                val parts = line.trim().split(" ")
                if (parts.size > 1) {
                    val word = parts[0]
                    val vector = parts.drop(1).map { it.toDouble() }
                    gloveMap[word] = vector
                }
            }
        }

        println("success: GloVe model loaded in memory!")
    }

    fun getVectorForWord(word: String): List<Double>? {
        return gloveMap[word]
    }
}
