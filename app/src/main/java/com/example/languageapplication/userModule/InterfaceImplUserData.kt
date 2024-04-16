package com.example.languageapplication.userModule

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.example.languageapplication.signUpInModule.EMAIL_PATTERN
import com.example.languageapplication.signUpInModule.ModelUser
import com.example.languageapplication.signUpInModule.USERNAME_PATTERN
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern

class InterfaceImplUserData : UserDataInterface {
    private val supabaseClient: SupabaseClient by lazy {
        createSupabaseClient(PROJECT_URL, SUPABASE_KEY) { install(Postgrest) }
    }

    override suspend fun getExistedUser(email: String, password: String): List<ModelUser> = withContext(Dispatchers.IO) {
        supabaseClient.from("usersData")
            .select(columns = Columns.list("firstname", "lastname", "userImage", "email", "password", "score")) {
                filter {
                    eq("email", email)
                    eq("password", hashedPass(password))
                }
            }.decodeList<ModelUser>()
    }

    override fun registerNewUser(userModel: ModelUser) {
        CoroutineScope(Dispatchers.IO).launch {
            supabaseClient.from("usersData").insert(userModel)
        }
    }

    override fun onBoardingCompleted(context: Context) {
        context.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(FINISHED_STATE_KEY, true)
            .apply()
    }

    override fun isOnBoardingCompleted(requireActivity: FragmentActivity): Boolean =
        requireActivity.getSharedPreferences(PREF_COMPLETE_NAME, Context.MODE_PRIVATE)
            .getBoolean(FINISHED_STATE_KEY, false)

    override fun makeAuthSharedFlag(context: Context, email: String, password: String) {
        context.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE).edit().apply {
            putBoolean(AUTHORIZED_STATE_KEY, true)
            putString(EMAIL_KEY, email)
            putString(PASSWORD_KEY, password)
            apply()
        }
    }

    override fun getAuthorizedEmailPass(requireActivity: FragmentActivity): List<String?> =
        requireActivity.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE).let {
            listOf(it.getString(EMAIL_KEY, ""), it.getString(PASSWORD_KEY, ""))
        }

    override fun isAnyoneAuthorized(requireActivity: FragmentActivity): Boolean =
        requireActivity.getSharedPreferences(PREF_AUTH_NAME, Context.MODE_PRIVATE)
            .getBoolean(AUTHORIZED_STATE_KEY, false)

    override fun putDataUserProfileScreen(userModel: ModelUser, context: Context) {
        context.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE).edit().apply {
            putString(USER_FIRST_NAME, userModel.firstname)
            putString(USER_LAST_NAME, userModel.lastname)
            putString(USER_EMAIL, userModel.email)
            putString(USER_IMAGE, userModel.userImage)
            apply()
        }
    }

    override fun getDataUserProfileScreen(requireActivity: FragmentActivity): ModelUser =
        requireActivity.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE).run {
            ModelUser(
                firstname = getString(USER_FIRST_NAME, "")!!,
                lastname = getString(USER_LAST_NAME, "")!!,
                email = getString(USER_EMAIL, "")!!,
                userImage = getString(USER_IMAGE, "")!!
            )
        }

    override fun isValidData(data: String, needPattern: String): Boolean {
        val pattern = Pattern.compile(needPattern)
        return pattern.matcher(data).matches() && data.isNotEmpty()
    }

    override fun isEverythingOkay(userData: ModelUser): Boolean =
        isValidData(userData.firstname, USERNAME_PATTERN) &&
                isValidData(userData.lastname, USERNAME_PATTERN) &&
                isValidData(userData.email, EMAIL_PATTERN)

    override fun hashedPass(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(64, '0')
    }

    override suspend fun getTopUsers(): List<ModelUser> = withContext(Dispatchers.IO) {
        supabaseClient.from("usersData")
            .select(columns = Columns.list("firstname", "lastname", "userImage", "email", "password", "score"))
            .decodeList<ModelUser>()
            .sortedByDescending { it.score }
            .take(3)
    }
}
