package com.onefootball.di

import com.onefootball.app.MyNewsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Dmitry Tkachuk
 * Created on 07.02.2020
 * I used here dagger to provide viewmodel factory and context.Additionally,it could be extended
 * with another instances like Retrofit,OkHttp,etc.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, ActivityModule::class,
        MainModule::class))
interface MyNewsComponent : AndroidInjector<MyNewsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(application: MyNewsApplication): Builder

        fun build(): MyNewsComponent
    }
}