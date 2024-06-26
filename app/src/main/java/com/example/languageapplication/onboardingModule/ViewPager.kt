package com.example.languageapplication.onboardingModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.languageapplication.R
import com.example.languageapplication.onboardingModule.screens.FirstScreenOnb
import com.example.languageapplication.onboardingModule.screens.SecondScreenOnb
import com.example.languageapplication.onboardingModule.screens.ThirdScreenOnb

class ViewPager : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf (
            FirstScreenOnb(),
            SecondScreenOnb(),
            ThirdScreenOnb()
        )

        val adapter = VPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val vPager = view.findViewById<ViewPager2>(R.id.viewPager)
        vPager.adapter = adapter

        return view
    }
}