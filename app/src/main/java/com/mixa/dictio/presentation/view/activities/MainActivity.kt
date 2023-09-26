package com.mixa.dictio.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mixa.dictio.R
import com.mixa.dictio.data.models.entities.DictionaryEntity
import com.mixa.dictio.databinding.ActivityMainBinding
import com.mixa.dictio.presentation.contract.MainContract
import com.mixa.dictio.presentation.view.fragments.DictionariesFragment
import com.mixa.dictio.presentation.viewmodels.DictionaryViewModel
import com.mixa.dictio.presentation.viewmodels.TermViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    //We can access all the UI xml elements from the binding.
    lateinit var binding: ActivityMainBinding
    //View model injected by Koin which we use to communicate with the database.
    val dictionaryViewModel: MainContract.DictionaryViewModel by viewModel<DictionaryViewModel>()
    val termViewModel: MainContract.TermViewModel by viewModel<TermViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Shows the SplashScreen from the res/values/splash.xml
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction().add(R.id.mainActivityLayout, DictionariesFragment()).commit()

        setContentView(binding.root)

        init()
    }

    /**
     * Initializes all necessary things for us,
     * is called in the onCreate function.
     */
    private fun init(){
        initUI()
        initDB()
    }

    /**
     * Initializes the user interface components.
     */
    private fun initUI(){
        supportActionBar?.title = getString(R.string.mydictionaries)
    }

    /**
     * Inserts predefined dictionaries into the database
     */
    private fun initDB(){
        val defaultLanguages = mutableListOf(
            DictionaryEntity(1, getString(R.string.italian), R.drawable.italian.toString(), "it"),
            DictionaryEntity(2, getString(R.string.french), R.drawable.french.toString(), "fr"),
            DictionaryEntity(3, getString(R.string.german), R.drawable.german.toString(), "gr"),
            DictionaryEntity(4, getString(R.string.arabic), R.drawable.arabic.toString(), "ar"),
            DictionaryEntity(5, getString(R.string.azerbaijani), R.drawable.azerbaijani.toString(), "az"),
            DictionaryEntity(6, getString(R.string.catalan), R.drawable.catalan.toString(), "ca"),
            DictionaryEntity(7, getString(R.string.chinese), R.drawable.chinese.toString(), "zh"),
            DictionaryEntity(8, getString(R.string.czech), R.drawable.czech.toString(), "ch"),
            DictionaryEntity(9, getString(R.string.danish), R.drawable.danish.toString(), "da"),
            DictionaryEntity(10, getString(R.string.dutch), R.drawable.dutch.toString(), "nl"),
            DictionaryEntity(11, getString(R.string.finnish), R.drawable.finnish.toString(), "fi"),
            DictionaryEntity(12, getString(R.string.greek), R.drawable.greek.toString(), "el"),
            DictionaryEntity(13, getString(R.string.japanese), R.drawable.japanese.toString(), "ja"),
            DictionaryEntity(14, getString(R.string.korean), R.drawable.korean.toString(), "ko"),
            DictionaryEntity(15, getString(R.string.polish), R.drawable.polish.toString(), "pl"),
            DictionaryEntity(16, getString(R.string.russian), R.drawable.russian.toString(), "ru"),
            DictionaryEntity(17, getString(R.string.portuguese), R.drawable.portuguese.toString(), "pt"),
            DictionaryEntity(18, getString(R.string.slovak), R.drawable.slovak.toString(), "sk"),
            DictionaryEntity(19, getString(R.string.spanish), R.drawable.spanish.toString(), "es"),
            DictionaryEntity(20, getString(R.string.swedish), R.drawable.swedish.toString(), "sv"),
            DictionaryEntity(21, getString(R.string.turkish), R.drawable.turkish.toString(), "tr"),
            DictionaryEntity(22, getString(R.string.ukrainian), R.drawable.ukrainian.toString(), "uk")
        )
        dictionaryViewModel.insertAll(defaultLanguages)
    }
}