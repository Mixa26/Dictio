package com.mixa.dictio.presentation.view.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mixa.dictio.R
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.databinding.ActivityMainBinding
import com.mixa.dictio.databinding.DialogAddDictionaryBinding
import com.mixa.dictio.presentation.contract.MainContract
import com.mixa.dictio.presentation.view.states.DeleteDictionaryState
import com.mixa.dictio.presentation.view.states.DictionaryState
import com.mixa.dictio.presentation.view.states.InsertDictionaryState
import com.mixa.dictio.presentation.viewmodels.DictionaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    val dictionaryViewModel: MainContract.DictionaryViewModel by viewModel<DictionaryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
    }

    private fun init(){
        initUI()
        initListeners()
        initObservers()
    }

    private fun initUI(){
        supportActionBar?.title = getString(R.string.mydictionaries)
    }

    private fun initListeners(){
        binding.addDictionaryButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.buttonAddDictionary)

            val binding: DialogAddDictionaryBinding = DialogAddDictionaryBinding.inflate(layoutInflater)

            val dialog = builder.create()

            dialog.setView(binding.root)

            binding.createDictionaryButton.setOnClickListener{

                //Adding the dictionary to the database
                val newDict = DictionaryEntity(null, binding.languageInput.text.toString())
                dictionaryViewModel.insert(newDict)

                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun initObservers(){
        dictionaryViewModel.dictionaryState.observe(this) {
            renderGetDictState(it)
        }
        dictionaryViewModel.insertDictionaryState.observe(this) {
            renderInsertDictState(it)
        }
        dictionaryViewModel.deleteDictionaryState.observe(this) {
            renderDeleteDictState(it)
        }
    }

    private fun renderGetDictState(state: DictionaryState){
        when(state){
            is DictionaryState.Success -> {
                TODO("Add the gathered dictionaries to RecyclerView" +
                        "or to some kind of list and then to RecyclerView")
            }
            is DictionaryState.Error -> {
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderInsertDictState(state: InsertDictionaryState){
        when(state){
            is InsertDictionaryState.Success -> {
                TODO("Add the new dictionary to RecyclerView")
            }
            is InsertDictionaryState.Error -> {
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderDeleteDictState(state: DeleteDictionaryState){
        when(state){
            is DeleteDictionaryState.Success -> {
                TODO("Delete the dictionary from RecyclerView" +
                        "and from a temporary list if any was mode")
            }
            is DeleteDictionaryState.Error -> {
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }
}