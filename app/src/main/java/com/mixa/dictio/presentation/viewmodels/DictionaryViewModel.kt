package com.mixa.dictio.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mixa.dictio.R
import com.mixa.dictio.data.models.entities.DictionaryEntity
import com.mixa.dictio.data.repository.DictionaryRepository
import com.mixa.dictio.presentation.contract.MainContract
import com.mixa.dictio.presentation.view.states.DeleteDictionaryState
import com.mixa.dictio.presentation.view.states.DictionaryState
import com.mixa.dictio.presentation.view.states.InsertDictionaryState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Regulates requests from the view to the model, hence the name ViewModel...get it eh?...nvm.
 */
class DictionaryViewModel(
    private val dictionaryRepository: DictionaryRepository
): ViewModel(), MainContract.DictionaryViewModel {

    private val subscriptions = CompositeDisposable()
    override val dictionaryState: MutableLiveData<DictionaryState> = MutableLiveData()
    override val insertDictionaryState: MutableLiveData<InsertDictionaryState?> = MutableLiveData()
    override val deleteDictionaryState: MutableLiveData<DeleteDictionaryState?> = MutableLiveData()

    /**
     * Sets the state to null so that if we enter the fragment/action again,
     * and the last time we added a dictionary, it doesn't trigger a response
     * like we added the dictionary now when entering the fragment/action
     * (it doesn't show the Snackbar). It basically "resets" the state..
     */
    override fun setInsertStateIdle() {
        insertDictionaryState.value = null
    }

    /**
     * Sets the state to null so that if we enter the fragment/action again,
     * and the last time we deleted a dictionary, it doesn't trigger a response
     * like we deleted the dictionary now when entering the fragment/action
     * (it doesn't show the Snackbar). It basically "resets" the state..
     */
    override fun setDeleteStateIdle() {
        deleteDictionaryState.value = null
    }

    /**
     * Get's all the dictionaries from the database and subscribes the result to be delivered
     * to the main thread.
     */
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

    /**
     * Inserts a dictionary to the database.
     */
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

    /**
     * Inserts multiple dictionaries to the database.
     */
    override fun insertAll(dictionary: List<DictionaryEntity>) {
        val subscription = dictionaryRepository
            .insertAll(dictionary)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {
                    insertDictionaryState.value = InsertDictionaryState.Error(R.string.errorInsertingDict)
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    /**
     * Deletes a dictionary from the database.
     */
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

    /**
     * Interrupts all ongoing requested in the subscriptions if the user switched to another screen
     * or something of a kind happens.
     */
    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}