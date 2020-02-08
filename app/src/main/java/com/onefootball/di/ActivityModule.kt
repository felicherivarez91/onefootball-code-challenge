package com.onefootball.di

import com.onefootball.view.MyNewsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 * All rights reserved
 */
@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun inject(): MyNewsActivity
}
