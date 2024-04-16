package com.example.languageapplication.onboardingModule.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import com.example.languageapplication.userModule.InterfaceImplUserData

class ThirdScreenOnb : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third_screen_onboarding, container, false)
        val btnFinish = view.findViewById<Button>(R.id.btn_choose_lang)
        val userDataLogicImpl = InterfaceImplUserData()

        btnFinish.setOnClickListener {
            userDataLogicImpl.onBoardingCompleted(requireActivity())
            findNavController().navigate(R.id.action_viewPagerFragment_to_chooseLanguage)
        }

        return view
    }
}