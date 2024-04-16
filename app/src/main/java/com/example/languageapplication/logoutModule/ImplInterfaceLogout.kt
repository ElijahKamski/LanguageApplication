package com.example.languageapplication.logoutModule

import android.content.Context
import com.example.languageapplication.userModule.PREF_AUTH_NAME
import com.example.languageapplication.userModule.PREF_COMPLETE_NAME
import com.example.languageapplication.userModule.USER_DATA_KEY

class ImplInterfaceLogout : InterfaceLogout {

    override fun clearOnBoardingFlag(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    override fun clearAuthFlag(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    override fun clearUserData(context: Context) {
        val sharedPref = context.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }
}