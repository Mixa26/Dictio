package com.mixa.dictio.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mixa.dictio.R
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.presentation.recycler.differ.DictionaryDiffItemCallback
import com.mixa.dictio.presentation.view.activities.MainActivity

class DictionaryAdapter(diffCallback: DictionaryDiffItemCallback): ListAdapter<DictionaryEntity, DictionaryAdapter.DictionaryViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dictionary_item, parent, false))
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener{
            Toast.makeText(holder.itemView.context, holder.itemView.findViewById<TextView>(R.id.languageTitle).text, Toast.LENGTH_LONG).show()
        }
    }

    class DictionaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(dictionary: DictionaryEntity){
            itemView.findViewById<TextView>(R.id.languageTitle).text = dictionary.language

            itemView.findViewById<ImageView>(R.id.deleteDictImage).setOnClickListener{
                val mainActivity = (itemView.context as MainActivity)
                Snackbar.make(mainActivity.binding.root, R.string.confirmationDictDeletion, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.delete, View.OnClickListener { mainActivity.dictionaryViewModel.delete(dictionary) })
                    .show()
            }
        }
    }
}