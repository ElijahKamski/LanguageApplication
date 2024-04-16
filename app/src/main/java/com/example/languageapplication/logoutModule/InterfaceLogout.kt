package com.example.languageapplication.logoutModule

import android.content.Context

interface InterfaceLogout {
    fun clearOnBoardingFlag(context: Context)
    fun clearAuthFlag(context: Context)
    fun clearUserData(context: Context)
}