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

    /**
     * When the view holder is created inflate its view (xml).
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dictionary_item, parent, false))
    }

    /**
     * When the view holder is binded to the recycler view this is triggered.
     */
    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))

        //If the dictionary is selected we want to open it's Terms page.
        holder.itemView.setOnClickListener{
            Toast.makeText(holder.itemView.context, holder.itemView.findViewById<TextView>(R.id.languageTitle).text, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * The class which holds one dictionary for the recycler view.
     */
    class DictionaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(dictionary: DictionaryEntity){
            //Set the language title on the item of the recycler view.
            itemView.findViewById<TextView>(R.id.languageTitle).text = dictionary.language

            //If the button for deleting a dictionary is selected, prompt the user with
            //a snackbar to see if he's sure of deletion.
            itemView.findViewById<ImageView>(R.id.deleteDictImage).setOnClickListener{
                val mainActivity = (itemView.context as MainActivity)
                Snackbar.make(mainActivity.binding.root, R.string.confirmationDictDeletion, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.delete, View.OnClickListener { mainActivity.dictionaryViewModel.delete(dictionary) })
                    .show()
            }
        }
    }
}