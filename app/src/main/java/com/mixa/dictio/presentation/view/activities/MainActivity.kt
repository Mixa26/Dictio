package com.mixa.dictio.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mixa.dictio.R
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
    }

    /**
     * Initializes the user interface components.
     */
    private fun initUI(){
        supportActionBar?.title = getString(R.string.mydictionaries)
    }


}