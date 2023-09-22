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

/**
 * Regulates requests from the view to the model, hence the name ViewModel...get it eh?...nvm.
 */
class TermViewModel(
    private val termRepository: TermRepository
): ViewModel() , MainContract.TermViewModel {

    private val subscriptions = CompositeDisposable()
    override val termState: MutableLiveData<TermState> = MutableLiveData()
    override val insertTermState: MutableLiveData<InsertTermState?> = MutableLiveData()
    override val deleteTermState: MutableLiveData<DeleteTermState?> = MutableLiveData()

    /**
     * Sets the state to null so that if we enter the fragment again,
     * and the last time we added a term, it doesn't trigger a response
     * like we added the term now when entering the fragment
     * (it doesn't show the Snackbar). It basically "resets" the state..
     */
    override fun setInsertStateIdle() {
        insertTermState.value = null
    }

    /**
     * Sets the state to null so that if we enter the fragment again,
     * and the last time we deleted a term, it doesn't trigger a response
     * like we deleted the term now when entering the fragment
     * (it doesn't show the Snackbar). It basically "resets" the state..
     */
    override fun setDeleteStateIdle() {
        deleteTermState.value = null
    }

    /**
     * Get's all the terms by dictionary id that it's connected to
     * from the database and subscribes the result to be delivered
     * to the main thread.
     */
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

    /**
     * Inserts a term to the database.
     */
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

    /**
     * Deletes a term from the database.
     */
    override fun delete(term: TermEntity) {
        val subscription = termRepository
            .delete(term)
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

    /**
     * Interrupts all ongoing requested in the subscriptions if the user switched to another screen
     * or something of a kind happens.
     */
    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}