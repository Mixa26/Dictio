package com.mixa.dictio.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mixa.dictio.R
import com.mixa.dictio.data.models.DictionaryEntity
import com.mixa.dictio.data.repository.DictionaryRepository
import com.mixa.dictio.presentation.contract.MainContract
import com.mixa.dictio.presentation.view.states.DeleteDictionaryState
import com.mixa.dictio.presentation.view.states.DictionaryState
import com.mixa.dictio.presentation.view.states.InsertDictionaryState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DictionaryViewModel(
    private val dictionaryRepository: DictionaryRepository
): ViewModel(), MainContract.DictionaryViewModel {

    private val subscriptions = CompositeDisposable()
    override val dictionaryState: MutableLiveData<DictionaryState> = MutableLiveData()
    override val insertDictionaryState: MutableLiveData<InsertDictionaryState> = MutableLiveData()
    override val deleteDictionaryState: MutableLiveData<DeleteDictionaryState> = MutableLiveData()

    override fun getAll() {
        val subscription = dictionaryRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    dictionaryState.value = DictionaryState.Success(it)
                },
                {
                    dictionaryState.value = DictionaryState.Error(R.string.errorGettingDicts)
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insert(dictionary: DictionaryEntity) {
        val subscription = dictionaryRepository
            .insert(dictionary)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    insertDictionaryState.value = InsertDictionaryState.Success
                },
                {
                    insertDictionaryState.value = InsertDictionaryState.Error(R.string.errorInsertingDict)
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun delete(dictionary: DictionaryEntity) {
        val subscription = dictionaryRepository
            .delete(dictionary)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deleteDictionaryState.value = DeleteDictionaryState.Success
                },
                {
                    deleteDictionaryState.value = DeleteDictionaryState.Error(R.string.errorDeletingDict)
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