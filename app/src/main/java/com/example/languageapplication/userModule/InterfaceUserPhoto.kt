package com.example.languageapplication.userModule

import android.graphics.Bitmap
import android.net.Uri

interface InterfaceUserPhoto {
    suspend fun loadImageUriToSupabase(userImage: ByteArray?, userEmail: String): String
    suspend fun getBitmapFromUri(imageUri: Uri): Bitmap
}