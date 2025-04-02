package com.g5.model

class Prompt {
    private var input: String = ""
    private var imageLink: String? = null 

    fun setInput(userInput: String) {
        input = userInput
    }

    fun getInput(): String {
        return input
    }

    fun setImageLink(userImageLink: String?) {
        imageLink = userImageLink
    }

    fun getImageLink(): String? {
        return imageLink
    }
}
