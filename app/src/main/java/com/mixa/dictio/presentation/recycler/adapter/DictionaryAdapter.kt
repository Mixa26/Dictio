package com.mixa.dictio.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mixa.dictio.R
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.presentation.recycler.differ.DictionaryDiffItemCallback

class DictionaryAdapter(diffCallback: DictionaryDiffItemCallback): ListAdapter<DictionaryEntity, DictionaryAdapter.DictionaryViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dictionary_item, parent, false))
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener{
            Toast.makeText(holder.itemView.context, holder.itemView.findViewById<TextView>(R.id.languageTitle).text, Toast.LENGTH_SHORT).show()
        }
    }

    class DictionaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(dictionary: DictionaryEntity){
            itemView.findViewById<TextView>(R.id.languageTitle).text = dictionary.language
        }
    }
}