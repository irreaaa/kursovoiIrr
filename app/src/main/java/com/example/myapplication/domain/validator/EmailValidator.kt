package com.example.myapplication.domain.validator

object EmailValidator: Validator {

    override fun <T> validate(value: T): Boolean{
        val email = value as? String ?: return false
        val regex = Regex("^([a-z0-9]+)@([a-z0-9]+)\\.([a-z]{2,})$")
        return email.matches(regex)
    }
}