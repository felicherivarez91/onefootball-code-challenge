package com.onefootball.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onefootball.model.DataSupply
import com.onefootball.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 */
class MyNewsViewModel constructor(private val dataSupply: DataSupply) : ViewModel() {

    var items = MutableLiveData<List<News>>()
    private var itemsDisposable = Disposables.disposed()

    fun loadNews() {
        if (itemsDisposable.isDisposed) {
            itemsDisposable = dataSupply.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    items.value = it
                }, ::handleError)
        }
    }

    fun observeTheNews(): LiveData<List<News>> {
        return items
    }

    private fun handleError(it: Throwable?) {
        it?.stackTrace
    }

    override fun onCleared() {
        itemsDisposable.dispose()
        super.onCleared()
    }
}
