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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import com.example.languageapplication.userModule.InterfaceImplUserData


const val PASSWORD_PATTERN = "^(?=\\S+$).{8,20}$"
const val EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
const val USERNAME_PATTERN = "^[a-zA-Z0-9_-]+$"


class SignUpScreenFragment : Fragment() {

    private val viewModel: VModelUser by activityViewModels()
    private val userDataLogicImpl = InterfaceImplUserData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up_screen, container, false)

        val firstNameEditText = rootView.findViewById<EditText>(R.id.editText_firstname)
        val lastNameEditText = rootView.findViewById<EditText>(R.id.editText_lastname)
        val emailEditText = rootView.findViewById<EditText>(R.id.editText_email)
        val signInTextView = rootView.findViewById<TextView>(R.id.tView_signIn)
        val continueButton = rootView.findViewById<Button>(R.id.btn_continue_signUp)

        signInTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signUpScreen_to_loginScreenFragment)
        }

        firstNameEditText.addTextChangedListener(getTextWatcher(USERNAME_PATTERN, firstNameEditText))
        lastNameEditText.addTextChangedListener(getTextWatcher(USERNAME_PATTERN, lastNameEditText))
        emailEditText.addTextChangedListener(getTextWatcher(EMAIL_PATTERN, emailEditText))

        continueButton.setOnClickListener {
            val firstNameData = firstNameEditText.text.toString()
            val lastNameData = lastNameEditText.text.toString()
            val emailData = emailEditText.text.toString()

            val userModel = ModelUser(
                firstname = firstNameData,
                lastname = lastNameData,
                email = emailData
            )

            if (userDataLogicImpl.isEverythingOkay(userModel)) {
                viewModel.setRegistrationData(userModel)
                findNavController().navigate(R.id.action_signUpScreen_to_signUpPassword)
            } else {
                Toast.makeText(requireContext(), "Enter correct data!", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }

    private fun getTextWatcher(pattern: String, editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), pattern)) {
                    editText.error = getErrorText(pattern)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun getErrorText(pattern: String): String {
        return when (pattern) {
            USERNAME_PATTERN -> "Incorrect format of name!"
            EMAIL_PATTERN -> "Incorrect email format!"
            else -> ""
        }
    }
}
