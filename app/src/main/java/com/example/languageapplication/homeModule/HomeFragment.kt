package com.example.languageapplication.homeModule

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languageapplication.R
import com.example.languageapplication.signUpInModule.ModelUser
import com.example.languageapplication.userModule.AdapterTopPeople
import com.example.languageapplication.userModule.InterfaceImplUserData
import com.example.languageapplication.userModule.InterfaceImplUserPhoto
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val userDataLogicImpl = InterfaceImplUserData()
        val userImageImpl = InterfaceImplUserPhoto()
        val authorizedEmailPass = userDataLogicImpl.getAuthorizedEmailPass(requireActivity())

        val recyclerView: RecyclerView = rootView.findViewById(R.id.rec_view_top_users)
        val textViewName: TextView = rootView.findViewById(R.id.name_textView)
        val imageViewPhoto: ImageView = rootView.findViewById(R.id.user_image)

        val btnFirstGame: Button = rootView.findViewById(R.id.btn_first_game)
        val btnSecondGame: Button = rootView.findViewById(R.id.btn_second_game)
        val btnThirdGame: Button = rootView.findViewById(R.id.btn_third_game)
        val btnFourthGame: Button = rootView.findViewById(R.id.btn_fourth_game)

        lifecycleScope.launch {
            val authorizedUserData = userDataLogicImpl.getExistedUser(
                email = authorizedEmailPass[0]!!,
                password = authorizedEmailPass[1]!!
            )

            authorizedUserData.getOrNull(0)?.let { user ->
                if (user.userImage.isNotEmpty()) {
                    val userImageBitmap = userImageImpl.getBitmapFromUri(user.userImage.toUri())
                    imageViewPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageViewPhoto.setImageBitmap(userImageBitmap)
                }

                textViewName.text = "${user.firstname} ${user.lastname}"

                userDataLogicImpl.putDataUserProfileScreen(
                    ModelUser(
                        firstname = user.firstname,
                        lastname = user.lastname,
                        email = user.email,
                        userImage = user.userImage
                    ), requireContext()
                )
            }

            val topUsersData = userDataLogicImpl.getTopUsers()
            recyclerView.adapter = AdapterTopPeople(topUsersData)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        imageViewPhoto.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
        }

        btnFirstGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_firstGameFragment)
        }

        btnSecondGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_secondGameFragment)
        }

        btnThirdGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_thirdGameFragment)
        }

        btnFourthGame.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fourthGameFragment)
        }

        return rootView
    }
}
