package com.mixa.dictio.modules

import com.mixa.dictio.data.database.DictioDatabase
import com.mixa.dictio.data.repository.DictionaryRepository
import com.mixa.dictio.data.repository.DictionaryRepositoryImpl
import com.mixa.dictio.presentation.viewmodels.DictionaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dictionaryModule = module {
    viewModel { DictionaryViewModel(get()) }

    single<DictionaryRepository> { DictionaryRepositoryImpl(get()) }

    single { get<DictioDatabase>().getDictionaryDao() }
}