package com.example.languageapplication.splashModule

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import com.example.languageapplication.userModule.InterfaceImplUserData

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private val userDataLogicImpl = InterfaceImplUserData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            navigateBasedOnUserStatus()
        }, DELAY_MILLIS)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun navigateBasedOnUserStatus() {
        val actionId = when {
            userDataLogicImpl.isOnBoardingCompleted(requireActivity()) -> {
                if (userDataLogicImpl.isAnyoneAuthorized(requireActivity())) {
                    R.id.action_splashScreenFragment_to_homeFragment
                } else {
                    R.id.action_splashScreenFragment_to_loginScreenFragment
                }
            }
            else -> R.id.action_splashScreenFragment_to_viewPagerFragment
        }
        findNavController().navigate(actionId)
    }

    companion object {
        private const val DELAY_MILLIS = 2000L
    }
}
