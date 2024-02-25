package com.codelabs.agrimate.utils

object ValidationUtils {
    fun isNotEmpty(vararg args: String): Boolean {
        for (arg in args) {
            if (arg.isEmpty()) {
                return false
            }
        }
        return true
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
        return emailRegex.matches(email)
    }

    fun isPasswordValid(password: String): Boolean = password.length >= 8
}