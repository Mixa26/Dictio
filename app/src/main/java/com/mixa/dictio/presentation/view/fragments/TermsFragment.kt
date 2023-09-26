package com.mixa.dictio.presentation.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mixa.dictio.R
import com.mixa.dictio.data.models.entities.DictionaryEntity
import com.mixa.dictio.data.models.entities.TermEntity
import com.mixa.dictio.data.models.requests.TranslateRequest
import com.mixa.dictio.databinding.FragmentTermsBinding
import com.mixa.dictio.presentation.recycler.adapter.TermAdapter
import com.mixa.dictio.presentation.recycler.differ.TermDiffItemCallback
import com.mixa.dictio.presentation.view.activities.MainActivity
import com.mixa.dictio.presentation.view.states.DeleteTermState
import com.mixa.dictio.presentation.view.states.InsertTermState
import com.mixa.dictio.presentation.view.states.TermState
import com.mixa.dictio.presentation.view.states.TranslateState

class TermsFragment(private val dictionary: DictionaryEntity): Fragment() {
    //We can access all the UI xml elements from the binding.
    lateinit var binding: FragmentTermsBinding

    //Recycler view adapter for displaying our terms.
    //We submit our database lists to the adapter.
    private lateinit var recyclerViewAdapter: TermAdapter
    private var termsList: List<TermEntity> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentTermsBinding.inflate(layoutInflater)

        //We hide the action bar because we don't need it on the terms page
        (context as MainActivity).supportActionBar?.hide()

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
        binding.termTitle.text = dictionary.language
        initRecyclerView()
        if (dictionary.translationCode.isEmpty()){
            binding.termTranslationLayer.visibility = View.GONE
        }
        else{
            binding.termTranslationLayer.visibility = View.VISIBLE
        }
    }

    /**
     * Sets up the recycler view so it can be used to store data.
     */
    private fun initRecyclerView(){
        recyclerViewAdapter = TermAdapter(TermDiffItemCallback())
        binding.termRecyclerView.adapter = recyclerViewAdapter
        binding.termRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Initializes listeners for button presses and such.
     */
    private fun initListeners(){
        //The button for returning back to the dictionaries selection.
        binding.termBackButton.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.mainActivityLayout, DictionariesFragment()).commit()
        }

        //If this is false then it's a custom dictionary for which we do not have translation enabled.
        if (dictionary.translationCode.isNotEmpty()) {
            //When the translate button is pressed contact the API
            binding.termTranslateButton.setOnClickListener {
                if (binding.termTranslateInput.toString().isNotEmpty()) {
                    val request = TranslateRequest(
                        binding.termTranslateInput.toString(),
                        dictionary.translationCode,
                        "en",
                        "text",
                        ""
                    )
                    (context as MainActivity).termViewModel.translate(request)
                }
            }
        }

        binding.termSearchInput.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    refreshList(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            }
        )

        //When a term is added this listener is triggered on the button click.
        binding.addTermButton.setOnClickListener{
            if (binding.termForeign.text.toString().isNotEmpty() && binding.termMeaning.text.toString().isNotEmpty() && dictionary.id != null){
                //If everything is fine add the new term to the the database with the corresponding dictionary id.
                (context as MainActivity).termViewModel.insert(TermEntity(null, binding.termForeign.text.toString(), binding.termMeaning.text.toString(), dictionary.id))
                //Empty the input for the term translation after it's added.
                binding.termForeign.setText("")
                binding.termMeaning.setText("")
            }
            else{
                //The user didn't imput the text for the term and it's translation so we inform.
                Snackbar.make(binding.root, R.string.badInputTermAndTranslation, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Initializes observers for data incoming from the database.
     * When data is being requested, once its gathered it's
     * received here.
     */
    private fun initObservers(){
        (context as MainActivity).termViewModel.termState.observe(viewLifecycleOwner) {
            //Rendering of receiving data from the DB.
            renderGetTermState(it)
        }
        (context as MainActivity).termViewModel.insertTermState.observe(viewLifecycleOwner) {
            //Rendering of inserting data into the DB.
            renderInsertTermState(it)
        }
        (context as MainActivity).termViewModel.deleteTermState.observe(viewLifecycleOwner) {
            //Rendering of deleting data from the DB.
            renderDeleteTermState(it)
        }
        (context as MainActivity).termViewModel.translateState.observe(viewLifecycleOwner) {
            //Render the received translated text from the API
            renderTranslateState(it)
        }

        //We request data at the beginning so that we can present it to the user.
        if (dictionary.id != null) {
            (context as MainActivity).termViewModel.getAllByDictId(dictionary.id)
        }
    }

    /**
     * Regulates requested data for dictionaries by
     * submitting it to the recycler view to be
     * presented to the user.
     */
    private fun renderGetTermState(state: TermState){
        when(state){
            is TermState.Success -> {
                //If the action is successful, add new term to the recycler view.
                termsList = state.terms
                recyclerViewAdapter.submitList(state.terms)
                //Clear the search if new data has been found and gathered from the DB.
                binding.termSearchInput.setText("")
            }
            is TermState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Regulates a response after a term is inserted
     * into the database.
     */
    private fun renderInsertTermState(state: InsertTermState?){
        when(state){
            is InsertTermState.Success -> {
                //If the action is successful, show us a message in the Snackbar.
                Snackbar.make(binding.root, R.string.successInsertingTerm, Toast.LENGTH_SHORT).show()
                //We reset the state so it doesn't trigger the Snackbar if we enter
                //the fragment again
                (context as MainActivity).termViewModel.setInsertStateIdle()
            }
            is InsertTermState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    /**
     * Regulates a response after a term is deleted
     * from the database.
     */
    private fun renderDeleteTermState(state: DeleteTermState?){
        when(state){
            is DeleteTermState.Success -> {
                //If the action is successful, show us a message in the Snackbar.
                Snackbar.make(binding.root, R.string.successDeletingTerm, Toast.LENGTH_SHORT).show()
                //We reset the state so it doesn't trigger the Snackbar if we enter
                //the fragment again
                (context as MainActivity).termViewModel.setDeleteStateIdle()
            }
            is DeleteTermState.Error -> {
                //If the action was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    /**
     * Regulates the API response for the translated text.
     */
    private fun renderTranslateState(state: TranslateState){
        when(state){
            is TranslateState.Success -> {
                //If the word was translated correctly, input the foreign word
                //and it's translation in the slots for adding a new term.
                binding.termForeign.setText(binding.termTranslateInput.text.toString())
                //Clear the input for the translation.
                binding.termTranslateInput.setText("")
                binding.termMeaning.setText(state.translation.translatedText)
            }
            is TranslateState.Error -> {
                //If the translation was a failure, alert us through a Toast.
                Toast.makeText(context, state.messageResourceId, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Regulates which term to present based on the search
     */
    private fun refreshList(search: String){
        //If the search is empty bring back all the terms
        if (search.isEmpty()){
            recyclerViewAdapter.submitList(termsList)
        }

        val filteredList = mutableListOf<TermEntity>()

        //Find all the terms that contain the text inserted in the search input
        for (term in termsList){
            if (term.term.lowercase().contains(search.lowercase()) || term.translation.lowercase().contains(search.lowercase())){
                filteredList.add(term)
            }
        }

        recyclerViewAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //Make the action bar visible again because we're returning to
        //the dictionaries selection
        (context as MainActivity).supportActionBar?.show()
    }
}