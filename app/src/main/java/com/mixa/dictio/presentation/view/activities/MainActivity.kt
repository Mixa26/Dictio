package com.mixa.dictio.presentation.view.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mixa.dictio.R
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.databinding.ActivityMainBinding
import com.mixa.dictio.databinding.DialogAddDictionaryBinding
import com.mixa.dictio.presentation.contract.MainContract
import com.mixa.dictio.presentation.recycler.adapter.DictionaryAdapter
import com.mixa.dictio.presentation.recycler.differ.DictionaryDiffItemCallback
import com.mixa.dictio.presentation.view.states.DeleteDictionaryState
import com.mixa.dictio.presentation.view.states.DictionaryState
import com.mixa.dictio.presentation.view.states.InsertDictionaryState
import com.mixa.dictio.presentation.viewmodels.DictionaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    //We can access all the UI xml elements from the binding.
    lateinit var binding: ActivityMainBinding
    //View model injected by Koin which we use to communicate with the database.
    val dictionaryViewModel: MainContract.DictionaryViewModel by viewModel<DictionaryViewModel>()

    //Recycler view for displaying our dictionaries.
    lateinit var recyclerViewAdapter: DictionaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Shows the SplashScreen from the res/values/splash.xml
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

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
        supportActionBar?.title = getString(R.string.mydictionaries)
        initRecyclerView()
    }

    /**
     * Sets up the recycler view so it can be used to store data.
     */
    private fun initRecyclerView(){
        recyclerViewAdapter = DictionaryAdapter(DictionaryDiffItemCallback())
        binding.dictionaryRecyclerView.adapter = recyclerViewAdapter
        binding.dictionaryRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    /**
     * Initializes listeners for button presses and such.
     */
    private fun initListeners(){
        //When a button for adding a dictionary is clicked it creates a dialog.
        binding.addDictionaryButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.buttonAddDictionary)

            val binding: DialogAddDictionaryBinding = DialogAddDictionaryBinding.inflate(layoutInflater)

            val dialog = builder.create()

            dialog.setView(binding.root)

            //When the button create in the dialog is pressed.
            binding.createDictionaryButton.setOnClickListener{

                //Creating the new dictionary.
                val newDict = DictionaryEntity(null, binding.languageInput.text.toString())
                //Adding the dictionary to the database.
                dictionaryViewModel.insert(newDict)

                dialog.dismiss()
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
        dictionaryViewModel.dictionaryState.observe(this) {
            //Rendering of receiving data from the DB.
            renderGetDictState(it)
        }
        dictionaryViewModel.insertDictionaryState.observe(this) {
            //Rendering of inserting data into the DB.
            renderInsertDictState(it)
        }
        dictionaryViewModel.deleteDictionaryState.observe(this) {
            //Rendering of deleting data from the DB.
            renderDeleteDictState(it)
        }

        //We request data at the beginning so that we can present it to the user.
        dictionaryViewModel.getAll()
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
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Regulates a response after a dictionary is inserted
     * into the database.
     */
    private fun renderInsertDictState(state: InsertDictionaryState){
        when(state){
            is InsertDictionaryState.Success -> {
                //If the action is successful, show us a message in the Snackbar.
                Snackbar.make(binding.root, R.string.successInsertingDict, Toast.LENGTH_SHORT).show()
            }
            is InsertDictionaryState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Regulates a response after a dictionary is deleted
     * from the database
     */
    private fun renderDeleteDictState(state: DeleteDictionaryState){
        when(state){
            is DeleteDictionaryState.Success -> {
                //If the action is successful, show us a message in the Snackbar.
                Snackbar.make(binding.root, R.string.successDeletingDict, Toast.LENGTH_SHORT).show()
            }
            is DeleteDictionaryState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }
}