package com.example.languageapplication.chooseLanguagemodule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.languageapplication.R

class LanguageAdapter(private val list: List<LanguageModel>) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewLanguage: TextView = itemView.findViewById(R.id.text_view_language)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return LanguageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.textViewLanguage.text = list[position].languageName
        holder.itemView.setOnClickListener {
            when (list[position].languageName) {
                "Russian", "English" -> {
                    findNavController(holder.itemView).navigate(R.id.action_chooseLanguage_to_loginScreenFragment)
                }
            }
        }
        onItemClickListener?.onItemClick(position)
    }

}