package com.mixa.dictio.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mixa.dictio.R
import com.mixa.dictio.data.models.TermEntity
import com.mixa.dictio.data.repository.TermRepository
import com.mixa.dictio.presentation.contract.MainContract
import com.mixa.dictio.presentation.view.states.DeleteTermState
import com.mixa.dictio.presentation.view.states.InsertTermState
import com.mixa.dictio.presentation.view.states.TermState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TermViewModel(
    private val termRepository: TermRepository
): ViewModel() , MainContract.TermViewModel {

    private val subscriptions = CompositeDisposable()
    override val termState: MutableLiveData<TermState> = MutableLiveData()
    override val insertTermState: MutableLiveData<InsertTermState> = MutableLiveData()
    override val deleteTermState: MutableLiveData<DeleteTermState> = MutableLiveData()

    override fun getAllByDictId(dictionaryId: Int) {
        val subscription = termRepository
            .getAllByDictId(dictionaryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    termState.value = TermState.Success(it)
                },
                {
                    termState.value = TermState.Error( R.string.errorGettingDicts)
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insert(term: TermEntity) {
        val subscription = termRepository
            .insert(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    insertTermState.value = InsertTermState.Success
                },
                {
                    insertTermState.value = InsertTermState.Error(R.string.errorInsertingTerm)
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun delete(term: TermEntity) {
        val subscription = termRepository
            .insert(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deleteTermState.value = DeleteTermState.Success
                },
                {
                    deleteTermState.value = DeleteTermState.Error(R.string.errorDeletingTerm)
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}