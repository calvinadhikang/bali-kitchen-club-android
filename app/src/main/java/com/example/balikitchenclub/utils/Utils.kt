package com.example.balikitchenclub.utils

fun checkDigitInput(newValue: String): Int{
    if (newValue == "") {
        return 0
    }

    if (newValue.all { it.isDigit() }){
        return newValue.toInt()
    }else{
        return 0
    }
}