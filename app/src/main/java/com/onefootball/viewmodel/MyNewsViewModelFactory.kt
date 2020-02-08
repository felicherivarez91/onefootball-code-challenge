package com.onefootball.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onefootball.model.DataSupply
import javax.inject.Inject

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 */
@Suppress("UNCHECKED_CAST")
class MyNewsViewModelFactory @Inject constructor(private val dataSource: DataSupply) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MyNewsViewModel(dataSource) as T
}
