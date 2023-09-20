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
    lateinit var binding: ActivityMainBinding
    val dictionaryViewModel: MainContract.DictionaryViewModel by viewModel<DictionaryViewModel>()

    lateinit var recyclerViewAdapter: DictionaryAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recyclerViewAdapter = DictionaryAdapter(DictionaryDiffItemCallback())
        binding.dictionaryRecyclerView.adapter = recyclerViewAdapter
        binding.dictionaryRecyclerView.layoutManager = LinearLayoutManager(this)
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

        dictionaryViewModel.getAll()
    }

    private fun renderGetDictState(state: DictionaryState){
        when(state){
            is DictionaryState.Success -> {
                recyclerViewAdapter.submitList(state.dictionaries)
            }
            is DictionaryState.Error -> {
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderInsertDictState(state: InsertDictionaryState){
        when(state){
            is InsertDictionaryState.Success -> {
                Snackbar.make(binding.root, R.string.successInsertingDict, Toast.LENGTH_SHORT).show()
            }
            is InsertDictionaryState.Error -> {
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderDeleteDictState(state: DeleteDictionaryState){
        when(state){
            is DeleteDictionaryState.Success -> {
                Snackbar.make(binding.root, R.string.successDeletingDict, Toast.LENGTH_SHORT).show()
            }
            is DeleteDictionaryState.Error -> {
                Toast.makeText(this, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }
}