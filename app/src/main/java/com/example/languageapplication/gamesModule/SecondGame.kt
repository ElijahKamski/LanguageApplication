package com.example.languageapplication.gamesModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.languageapplication.R

class SecondGame : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_second_game, container, false)

        val firstAnswerButton = rootView.findViewById<Button>(R.id.button_first_answer)
        val secondAnswerButton = rootView.findViewById<Button>(R.id.button_second_answer)
        val thirdAnswerButton = rootView.findViewById<Button>(R.id.button_third_answer)
        val fourthAnswerButton = rootView.findViewById<Button>(R.id.button_fourth_answer)
        val nextButton = rootView.findViewById<Button>(R.id.button_next_answer)

        firstAnswerButton.setOnClickListener {
            showToast("Incorrect answer!")
        }
        secondAnswerButton.setOnClickListener {
            showToast("That's right!")
        }
        thirdAnswerButton.setOnClickListener {
            showToast("Incorrect answer!")
        }
        fourthAnswerButton.setOnClickListener {
            showToast("Incorrect answer!")
        }
        nextButton.setOnClickListener {
            showToast("All questions were shown!")
        }

        return rootView
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
