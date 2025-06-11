package com.example.myapplication.domain.validator

object PasswordValidator: Validator {
    override fun <T> validate(value: T): Boolean {
        val password = value as String
        if(password.length < 8) return false
        val numberRegex = Regex("[0-9]")
        val letterRegex = Regex("[A-Z]")
        val symbolRegex = Regex("[ !@#\$%^&*]")

        if(!password.contains(numberRegex)) return false
        if(!password.contains(letterRegex)) return false
        if(!password.contains(symbolRegex)) return false
        return true
    }
}