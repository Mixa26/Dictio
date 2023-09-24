package com.mixa.dictio.presentation.view.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mixa.dictio.R
import com.mixa.dictio.data.models.entities.DictionaryEntity
import com.mixa.dictio.databinding.DialogAddDictionaryBinding
import com.mixa.dictio.databinding.FragmentDictionariesBinding
import com.mixa.dictio.presentation.recycler.adapter.DictionaryAdapter
import com.mixa.dictio.presentation.recycler.differ.DictionaryDiffItemCallback
import com.mixa.dictio.presentation.view.activities.MainActivity
import com.mixa.dictio.presentation.view.states.DeleteDictionaryState
import com.mixa.dictio.presentation.view.states.DictionaryState
import com.mixa.dictio.presentation.view.states.InsertDictionaryState
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

class DictionariesFragment: Fragment() {
    //We can access all the UI xml elements from the binding.
    private lateinit var binding: FragmentDictionariesBinding

    //Recycler view adapter for displaying our dictionaries.
    //We submit our database lists to the adapter.
    private lateinit var recyclerViewAdapter: DictionaryAdapter

    //Data for the dialog of creating a dictionary.
    //These will be used for a custom language flag picture choosing.
    private val pictureChosingIntentCode = 1
    private var imgFile: String = ""
    private lateinit var languageFlag: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDictionariesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    /**
     * Initializes all necessary things for us,
     * is called in the onCreate function.
     */
    private fun init(){
        initUI()
        initListeners()
        initObservers()
    }

    /**
     * Initializes the user interface components.
     */
    private fun initUI(){
        initRecyclerView()
    }

    /**
     * Sets up the recycler view so it can be used to store data.
     */
    private fun initRecyclerView(){
        recyclerViewAdapter = DictionaryAdapter(DictionaryDiffItemCallback())
        binding.dictionaryRecyclerView.adapter = recyclerViewAdapter
        binding.dictionaryRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Initializes listeners for button presses and such.
     */
    private fun initListeners(){
        //When a button for adding a dictionary is clicked it creates a dialog.
        binding.addDictionaryButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.buttonAddDictionary)

            val binding: DialogAddDictionaryBinding = DialogAddDictionaryBinding.inflate(layoutInflater)

            val dialog = builder.create()

            dialog.setView(binding.root)

            //Set the language flag variable so we can insert a picture later.
            languageFlag = binding.languageFlag
            //Set the default picture for the language flag.
            Picasso.get().load(R.drawable.dictioicon).into(binding.languageFlag)

            //Start the picture selecting intent if the photo is clicked
            binding.languageFlag.setOnClickListener{
                val pickImageIntent = Intent()
                pickImageIntent.type = "image/*"
                pickImageIntent.action = Intent.ACTION_PICK
                startActivityForResult(Intent.createChooser(pickImageIntent, getString(R.string.pictureSelect)), pictureChosingIntentCode)
            }

            //When the button create in the dialog is pressed.
            binding.createDictionaryButton.setOnClickListener{

                if (binding.languageInput.text.toString().isNotEmpty()) {
                    //Creating the new dictionary.
                    val newDict =
                        DictionaryEntity(null, binding.languageInput.text.toString(), imgFile)
                    //Adding the dictionary to the database.
                    (context as MainActivity).dictionaryViewModel.insert(newDict)

                    dialog.dismiss()
                }
                else{
                    Snackbar.make(binding.root, R.string.badInputDictionaryCreation, Snackbar.LENGTH_LONG).show()
                }
            }

            dialog.show()
        }
    }

    /**
     * Initializes observers for data incoming from the database.
     * When data is being requested, once its gathered it's
     * received here.
     */
    private fun initObservers(){
        (context as MainActivity).dictionaryViewModel.dictionaryState.observe(viewLifecycleOwner) {
            //Rendering of receiving data from the DB.
            renderGetDictState(it)
        }
        (context as MainActivity).dictionaryViewModel.insertDictionaryState.observe(viewLifecycleOwner) {
            //Rendering of inserting data into the DB.
            renderInsertDictState(it)
        }
        (context as MainActivity).dictionaryViewModel.deleteDictionaryState.observe(viewLifecycleOwner) {
            //Rendering of deleting data from the DB.
            renderDeleteDictState(it)
        }

        //We request data at the beginning so that we can present it to the user.
        (context as MainActivity).dictionaryViewModel.getAll()
    }

    /**
     * Regulates requested data for dictionaries by
     * submitting it to the recycler view to be
     * presented to the user.
     */
    private fun renderGetDictState(state: DictionaryState){
        when(state){
            is DictionaryState.Success -> {
                //If the action is successful, add new dictionary to the recycler view.
                recyclerViewAdapter.submitList(state.dictionaries)
            }
            is DictionaryState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Regulates a response after a dictionary is inserted
     * into the database.
     */
    private fun renderInsertDictState(state: InsertDictionaryState?){
        when(state){
            is InsertDictionaryState.Success -> {
                //If the action is successful, show us a message in the Snackbar.
                Snackbar.make(binding.root, R.string.successInsertingDict, Toast.LENGTH_SHORT).show()
                //We reset the state so it doesn't trigger the Snackbar if we enter
                //the fragment again
                (context as MainActivity).dictionaryViewModel.setInsertStateIdle()
            }
            is InsertDictionaryState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    /**
     * Regulates a response after a dictionary is deleted
     * from the database
     */
    private fun renderDeleteDictState(state: DeleteDictionaryState?){
        when(state){
            is DeleteDictionaryState.Success -> {
                //If the action is successful, show us a message in the Snackbar.
                Snackbar.make(binding.root, R.string.successDeletingDict, Toast.LENGTH_SHORT).show()
                //We reset the state so it doesn't trigger the Snackbar if we enter
                //the fragment again
                (context as MainActivity).dictionaryViewModel.setDeleteStateIdle()
            }
            is DeleteDictionaryState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    //Receives the result of picture choosing for the language flag.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == pictureChosingIntentCode && resultCode == Activity.RESULT_OK){
            imgFile = data?.data.toString()

            Picasso.get().load(imgFile).into(languageFlag as ImageView)
        }
    }
}