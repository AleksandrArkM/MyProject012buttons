package com.example.myproject003.viewmodel

open class CountCheck (numberInput123: Int) {

    open val outputNumber = countCheck123 (numberInput123)

    open fun countCheck123(numberInput: Int): String {
        val number = when (numberInput) {
            in 0..999 -> "$numberInput"
            in 1000..1099 -> "${numberInput / 1000}K"
            in 1100..9999 -> "${numberInput / 1000}.${(numberInput / 100) % 10}K"
            in 10000..999999 -> "${numberInput / 1000}K"
            else -> "${numberInput / 1000000}.${(numberInput / 100000) % 10}M"
        }
        return number
    }
}