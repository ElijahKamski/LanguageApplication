package com.example.languageapplication.signUpInModule

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import com.example.languageapplication.userModule.InterfaceImplUserData
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passEditText: EditText
    private val userDataLogicImpl = InterfaceImplUserData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_screen, container, false)

        val loginButton = rootView.findViewById<Button>(R.id.btn_login)
        val signUpTextView = rootView.findViewById<TextView>(R.id.tView_signup)
        emailEditText = rootView.findViewById(R.id.editText_email_login)
        passEditText = rootView.findViewById(R.id.editText_pass_login)

        setEditTextWatchers()

        loginButton.setOnClickListener { loginUser() }

        signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreenFragment_to_signUpScreen)
        }

        return rootView
    }

    private fun setEditTextWatchers() {
        emailEditText.addTextChangedListener(getTextWatcher(EMAIL_PATTERN, emailEditText))
        passEditText.addTextChangedListener(getTextWatcher(PASSWORD_PATTERN, passEditText))
    }

    private fun getTextWatcher(pattern: String, editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), pattern)) {
                    editText.error = "Input valid data!"
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passEditText.text.toString()

        if (userDataLogicImpl.isValidData(email, EMAIL_PATTERN) &&
            userDataLogicImpl.isValidData(password, PASSWORD_PATTERN)
        ) {
            lifecycleScope.launch {
                if (userDataLogicImpl.getExistedUser(email, password).isNotEmpty()) {
                    userDataLogicImpl.makeAuthSharedFlag(requireContext(), email, password)
                    findNavController().navigate(R.id.action_loginScreenFragment_to_homeFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Check your data or sign up.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Enter valid data!", Toast.LENGTH_SHORT).show()
        }
    }
}
