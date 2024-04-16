package com.example.languageapplication.signUpInModule

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.languageapplication.R
import com.example.languageapplication.userModule.InterfaceImplUserData

class SignUpEndFragment : Fragment() {

    private val viewModel: VModelUser by activityViewModels()
    private val userDataLogicImpl = InterfaceImplUserData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up_password, container, false)

        val textViewSignIn = rootView.findViewById<TextView>(R.id.tView_signIn_third)
        val buttonSignUp = rootView.findViewById<Button>(R.id.btn_complete_signUp)
        val editTextPass = rootView.findViewById<EditText>(R.id.editText_pass)
        val editTextConfirmedPass = rootView.findViewById<EditText>(R.id.editText_confirm_pass)

        textViewSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpPassword_to_loginScreenFragment)
        }

        editTextPass.addTextChangedListener(getPasswordTextWatcher(editTextPass))

        buttonSignUp.setOnClickListener {
            val password = editTextPass.text.toString()
            val confirmedPassword = editTextConfirmedPass.text.toString()

            if (password != confirmedPassword) {
                showToast("Passwords aren't the same!")
                return@setOnClickListener
            }

            if (!userDataLogicImpl.isValidData(password, PASSWORD_PATTERN)) {
                showToast("Password should contain at least 8 characters!")
                return@setOnClickListener
            }

            val userWithPassword = viewModel.combineWithPassword(userDataLogicImpl.hashedPass(password))
            if (userWithPassword != null) {
                userDataLogicImpl.registerNewUser(userWithPassword)
                findNavController().navigate(R.id.action_signUpPassword_to_loginScreenFragment)
            }
        }

        return rootView
    }

    private fun getPasswordTextWatcher(editText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!userDataLogicImpl.isValidData(s.toString(), PASSWORD_PATTERN)) {
                    editText.error = "Incorrect password format!"
                } else {
                    editText.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
