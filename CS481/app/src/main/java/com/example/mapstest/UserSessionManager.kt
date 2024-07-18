package com.example.mapstest

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class UserSessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserUID(uid: Int) {
        Log.d("UserSessionManager", "Saving UID: $uid")
        val editor = prefs.edit()
        editor.putInt(KEY_USER_UID, uid)
        editor.apply()
        Log.d("UserSessionManager", "UID saved: ${prefs.getInt(KEY_USER_UID, -1)}")
    }

    fun getUserUID(): Int {
        val uid = prefs.getInt(KEY_USER_UID, -1)
        Log.d("UserSessionManager", "Retrieved UID: $uid")
        return uid
    }

    companion object {
        private const val PREFS_NAME = "user_session"
        private const val KEY_USER_UID = "user_uid"
    }
}
