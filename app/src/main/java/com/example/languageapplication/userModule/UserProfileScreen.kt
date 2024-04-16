package com.example.languageapplication.userModule

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import com.example.languageapplication.logoutModule.ImplInterfaceLogout
import com.example.languageapplication.signUpInModule.ModelUser
import kotlinx.coroutines.launch

class UserProfileScreen : Fragment() {

    private lateinit var imageViewAvatar: ImageView
    private lateinit var buttonChangeAvatar: Button
    private lateinit var userData: ModelUser
    private val userDataLogicImpl by lazy { InterfaceImplUserData() }
    private val logoutMethodsImpl by lazy { ImplInterfaceLogout() }
    private val userImImpl by lazy { InterfaceImplUserPhoto() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        setupUI(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI(view: View) {
        with(view) {
            userData = userDataLogicImpl.getDataUserProfileScreen(requireActivity())

            imageViewAvatar = findViewById(R.id.imageView_avatar)
            buttonChangeAvatar = findViewById(R.id.button_change_avatar)
            findViewById<TextView>(R.id.textView_username).text = "${userData.firstname} ${userData.lastname}"

            if (userData.userImage.isNotEmpty()) {
                loadImageAsync(userData.userImage)
            }

            buttonChangeAvatar.setOnClickListener { pickImageFromGallery() }
            findViewById<Button>(R.id.button_logout).setOnClickListener { performLogout() }
        }
    }

    private fun loadImageAsync(imageUri: String) {
        lifecycleScope.launch {
            imageViewAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViewAvatar.setImageBitmap(
                userImImpl.getBitmapFromUri(imageUri.toUri())
            )
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val imageUri = result.data?.data ?: return@registerForActivityResult
            handleImageResult(imageUri)
        }
    }

    private fun handleImageResult(imageUri: Uri) {
        lifecycleScope.launch {
            val userImageBitmap = userImImpl.getBitmapFromUri(imageUri)
            imageViewAvatar.setImageBitmap(userImageBitmap)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        launcher.launch(intent)
    }

    private fun performLogout() {
        logoutMethodsImpl.run {
            clearOnBoardingFlag(requireContext())
            clearAuthFlag(requireContext())
            clearUserData(requireContext())
        }
        findNavController().navigate(R.id.action_userProfileFragment_to_loginScreenFragment)
    }
}
