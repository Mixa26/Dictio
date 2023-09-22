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
import com.mixa.dictio.data.models.entities.TermEntity
import com.mixa.dictio.presentation.recycler.differ.TermDiffItemCallback
import com.mixa.dictio.presentation.view.activities.MainActivity

class TermAdapter(diffItem: TermDiffItemCallback): ListAdapter<TermEntity, TermAdapter.TermViewHolder>(diffItem){

    /**
     * When the view holder is created inflate its view (xml).
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermViewHolder {
        return TermViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_term, parent, false))
    }

    /**
     * When the view holder is binded to the recycler view this is triggered.
     */
    override fun onBindViewHolder(holder: TermViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * The class which holds one term for the recycler view.
     */
    class TermViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(term: TermEntity){
            //Set the foreign term text and it's translation.
            itemView.findViewById<TextView>(R.id.termItemForeignTerm).text = term.term
            itemView.findViewById<TextView>(R.id.termItemItsMeaning).text = term.translation

            //If the button for deleting a term is selected, prompt the user with
            //a snackbar to see if he's sure of deletion.
            itemView.findViewById<ImageView>(R.id.deleteTermImage).setOnClickListener{
                val mainActivity = (itemView.context as MainActivity)
                Snackbar.make(mainActivity.binding.root, R.string.confirmationTermDeletion, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.delete, View.OnClickListener { mainActivity.termViewModel.delete(term) })
                    .show()
            }
        }
    }
}