package com.security.togar.myapplication.utils

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import java.security.SecureRandom

fun Context.showToastError() {
    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
}

fun Context.showToastSuccess() {
    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
}

fun Context.showToastView(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun generateRandomString(length: Int): String {
    val charset = "1234567890"
    val random = SecureRandom()
    val sb = StringBuilder(length)

    for (i in 0 until length) {
        val randomCharIndex = random.nextInt(charset.length)
        sb.append(charset[randomCharIndex])
    }

    return sb.toString()
}

fun EditText.checkEdt(): Boolean {
    return this.text.isNotBlank() || this.text.isNotEmpty()
}

object Local {
    fun saveData(context: Context, key: String, value: String) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(context: Context, key: String, defaultValue: String): String? {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }

    fun clearData(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(Constant.MY_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}