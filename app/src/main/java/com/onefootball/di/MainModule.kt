package com.onefootball.di

import android.content.Context
import com.onefootball.app.MyNewsApplication
import com.onefootball.model.DataSupply
import com.onefootball.viewmodel.MyNewsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 * All rights reserved
 */
@Module
class MainModule {

    @Provides
    fun provideContext(app: MyNewsApplication): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideFactory(dataSource: DataSupply) = MyNewsViewModelFactory(dataSource)

}