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

    private fun init(){
        initTimber()
        initKoin()
    }

    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
    }

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