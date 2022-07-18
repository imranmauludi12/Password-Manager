package com.demoapp.passwordmanager.core.di

import android.content.Context
import com.demoapp.passwordmanager.core.session.SessionManagement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionModule {

    @Provides
    @Singleton
    fun provideSessionManagement(@ApplicationContext context: Context): SessionManagement {
        return SessionManagement(context)
    }

}