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

fun return24HourTime(hour: String, minute: String): String {
    var hourModified = if (hour.toInt() < 10) { "0${hour}" } else { hour }
    var minuteModified = if (minute.toInt() < 10) { "0${minute}" } else { minute }
    return "${hourModified}:${minuteModified}"
}
