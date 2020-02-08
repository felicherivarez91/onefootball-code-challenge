package com.onefootball.app

import com.onefootball.di.DaggerMyNewsComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 * All rights reserved
 */
class MyNewsApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMyNewsComponent.builder().app(this).build()
    }

}