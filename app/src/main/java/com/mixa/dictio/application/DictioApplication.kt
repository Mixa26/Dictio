package com.mixa.dictio.application

import android.app.Application
import com.mixa.dictio.modules.dictionaryModule
import com.mixa.dictio.modules.roomModule
import com.mixa.dictio.modules.termModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class DictioApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    /**
     * Initializes all necessary things for us,
     * is called in the onCreate function.
     */
    private fun init(){
        initTimber()
        initKoin()
    }

    /**
     * We use timber for debugging purposes.
     */
    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
    }

    /**
     * Here we initialize Kotlin, it will be used for dependency injection in our project.
     * We can inject by viewModel() or by inject().
     * It injects our modules in which we can store data.
     */
    private fun initKoin(){
        startKoin{
            androidContext(this@DictioApplication)
            modules(
                roomModule,
                dictionaryModule,
                termModule
            )
        }
    }
}