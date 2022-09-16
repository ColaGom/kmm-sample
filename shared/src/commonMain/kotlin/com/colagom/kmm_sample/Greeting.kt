package com.colagom.kmm_sample

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}