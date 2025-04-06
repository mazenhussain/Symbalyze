package com.g5.util

import java.io.File

object GloveLoader {
    fun loadGloveModel(filePath: String): Map<String, List<Double>> {
        val gloveMap = mutableMapOf<String, List<Double>>()
        File(filePath).useLines { lines ->
            lines.forEach { line ->
                val parts = line.trim().split(" ")
                if (parts.size > 2) {
                    val word = parts[0]
                    val vector = parts.drop(1).map { it.toDouble() }
                    gloveMap[word] = vector
                }
            }
        }
        return gloveMap
    }
}
