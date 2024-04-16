package com.example.languageapplication.gamesModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import java.util.Locale

class FirstGame : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_game_screen, container, false)

        val editTextAnswer = view.findViewById<EditText>(R.id.editText_first_answer)
        val buttonCheck = view.findViewById<Button>(R.id.button_check_first_answer)

        buttonCheck.setOnClickListener {
            val userAnswer = editTextAnswer.text.toString()
            if (userAnswer.lowercase(Locale.ROOT) == "racoon") {
                findNavController().navigate(R.id.action_firstGameFragment_to_correctScreen)
            } else {
                findNavController().navigate(R.id.action_firstGameFragment_to_mistakeScreen)
            }
        }

        return view
    }
}