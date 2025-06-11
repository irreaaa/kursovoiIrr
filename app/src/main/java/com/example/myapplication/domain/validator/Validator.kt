package com.example.myapplication.domain.validator

interface Validator {
    fun <T> validate(value: T): Boolean
}