package com.example.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences {

    companion object {
        fun editSpIdNumber(spName: String, context: Context) {
            val sharedPreferences : SharedPreferences = context.getSharedPreferences(
                spName,
                Context.MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            var num = sharedPreferences.getString(spName, "0")?.toInt()
            if (num != null) {
                num++
            }
            editor.putString(spName, num.toString())
            editor.apply()
        }

        fun getNewSpIdNumber(spName: String, context: Context) : String? {
            val sharedPreferences : SharedPreferences = context.getSharedPreferences(
                spName,
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getString(spName, "0")
        }
    }
}