package com.mixa.dictio.modules

import com.mixa.dictio.data.database.DictioDatabase
import com.mixa.dictio.data.datasources.local.TermDao
import com.mixa.dictio.data.datasources.remote.TranslateService
import com.mixa.dictio.data.repository.TermRepository
import com.mixa.dictio.data.repository.TermRepositoryImpl
import com.mixa.dictio.presentation.viewmodels.TermViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val termModule = module {

    viewModel { TermViewModel(get()) }

    single<TermRepository> { TermRepositoryImpl(get(), get()) }

    single<TermDao> { get<DictioDatabase>().getTermDao() }

    single<TranslateService> { create(get()) }
}