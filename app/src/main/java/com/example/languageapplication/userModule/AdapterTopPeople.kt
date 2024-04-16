package com.example.languageapplication.userModule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.languageapplication.R
import com.example.languageapplication.signUpInModule.ModelUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterTopPeople(private val topUsersList: List<ModelUser>) :
    RecyclerView.Adapter<AdapterTopPeople.TopUsersViewHolder>() {

    class TopUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewAvatar: ImageView = itemView.findViewById(R.id.card_user_avatar)
        val textViewUserName: TextView = itemView.findViewById(R.id.card_user_name)
        val textViewScore: TextView = itemView.findViewById(R.id.card_user_score)
        val textPoints: TextView = itemView.findViewById(R.id.textView_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUsersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_top_users, parent, false)
        return TopUsersViewHolder(itemView)
    }

    override fun getItemCount(): Int = topUsersList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TopUsersViewHolder, position: Int) {
        val currentItem = topUsersList[position]
        if (currentItem.userImage.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val imageUri = currentItem.userImage.toUri()
                val userImageBitmap = InterfaceImplUserPhoto().getBitmapFromUri(imageUri)
                withContext(Dispatchers.Main) {
                    holder.imageViewAvatar.setImageBitmap(userImageBitmap)
                    holder.imageViewAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }
        } else {
            holder.imageViewAvatar.setImageResource(R.drawable.no_internet_icon)
        }

        holder.textViewUserName.text = "${currentItem.firstname} ${currentItem.lastname}"
        holder.textViewScore.text = currentItem.score.toString()
        holder.textPoints.text = " points"
    }
}
