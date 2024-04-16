package com.example.languageapplication.userModule

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage

class InterfaceImplUserPhoto : InterfaceUserPhoto {

    // Initialize the Supabase client once at the class level
    private val supabaseClient: SupabaseClient = createSupabaseClient(
        supabaseUrl = PROJECT_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Storage)
    }

    override suspend fun loadImageUriToSupabase(userImage: ByteArray?, userEmail: String): String {
        val bucket = supabaseClient.storage.from("users_avatars")

        val outputPath = "avatars/" + userEmail.hashCode().toString() + "_" + userImage.hashCode() + ".jpg"
        bucket.upload(outputPath, userImage!!, upsert = false)

        supabaseClient.from("usersData").update(
            {
                set("userImage", outputPath)
            }
        ) {
            filter {
                eq("email", userEmail)
            }
        }
        return outputPath
    }

    override suspend fun getBitmapFromUri(imageUri: Uri): Bitmap {
        val bucket = supabaseClient.storage.from("users_avatars")
        val bytes = bucket.downloadAuthenticated(imageUri.toString())
        return BitmapFactory.decodeStream(bytes.inputStream())
    }
}
