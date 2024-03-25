package com.example.balikitchenclub.utils

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(
    var context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUser(name:String, username: String, password: String, id: Int, role: String) {
        val editor = sharedPreferences.edit()
        editor.putInt("id", id)
        editor.putString("name", name)
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("role", role)
        editor.apply()
    }

    fun getUser(): User? {
        val name = sharedPreferences.getString("name", null)
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        val id = sharedPreferences.getInt("id", -1)
        val role = sharedPreferences.getString("role", null)

        if (username != null && password != null && id != -1 && role != null && name != null) {
            return User(id, name, username, password, role)
        }
        return null
    }

    fun clearUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

class User(
    var id: Int,
    var name: String,
    var username: String,
    var password: String,
    var role: String,
){

}