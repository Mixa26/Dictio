package com.mixa.dictio.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mixa.dictio.R
import com.mixa.dictio.data.models.entities.DictionaryEntity
import com.mixa.dictio.presentation.recycler.differ.DictionaryDiffItemCallback
import com.mixa.dictio.presentation.view.activities.MainActivity
import com.mixa.dictio.presentation.view.fragments.TermsFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.io.File

class DictionaryAdapter(diffCallback: DictionaryDiffItemCallback): ListAdapter<DictionaryEntity, DictionaryAdapter.DictionaryViewHolder>(diffCallback) {

    /**
     * When the view holder is created inflate its view (xml).
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false))
    }

    /**
     * When the view holder is binded to the recycler view this is triggered.
     */
    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * The class which holds one dictionary for the recycler view.
     */
    class DictionaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(dictionary: DictionaryEntity){
            //Set the language title for the dictionary
            itemView.findViewById<TextView>(R.id.languageTitle).text = dictionary.language

            //Set the custom flag image if there is one
            if (dictionary.flagImage.isNotEmpty()){
                if (dictionary.flagImage.startsWith("content")){
                    Picasso.get().load(dictionary.flagImage).into(itemView.findViewById<ImageView>(R.id.languageImage))
                }
                else{
                    (itemView.findViewById<ImageView>(R.id.languageImage) as ImageView).setImageResource(dictionary.flagImage.toInt())
                }
            }
            else{
                //If there is no image load the default Dictio icon
                (itemView.findViewById<ImageView>(R.id.languageImage) as ImageView).setImageResource(R.drawable.dictioicon)
            }

            //The default languages that come with the app
            //have translations codes and we don't want
            //to let the user delete them
            if (dictionary.translationCode.isNotEmpty()){
                //Remove the icon for deletion if it's a default language that comes with the app
                itemView.findViewById<ImageView>(R.id.deleteDictImage).visibility = View.GONE
            }
            else{
                //If the button for deleting a dictionary is selected, prompt the user with
                //a snackbar to see if he's sure of deletion.
                itemView.findViewById<ImageView>(R.id.deleteDictImage).setOnClickListener{
                    val mainActivity = (itemView.context as MainActivity)
                    Snackbar.make(mainActivity.binding.root, R.string.confirmationDictDeletion, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.delete, View.OnClickListener { mainActivity.dictionaryViewModel.delete(dictionary) })
                        .show()
                }
            }


            //If the dictionary is selected we want to open it's Terms page.
            itemView.setOnClickListener{
                //The dictionary id should never be null here because it's taken from the database
                //which uses the id as the primary key.
                //The reason we have let the id to be null in the DictionaryEntity is because
                //the DataEntity is first created with data and now id, inserted into the database
                //and then it get is id.
                if (dictionary.id != null){
                    (itemView.context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.mainActivityLayout, TermsFragment(dictionary)).commit()
                }
            }
        }
    }
}