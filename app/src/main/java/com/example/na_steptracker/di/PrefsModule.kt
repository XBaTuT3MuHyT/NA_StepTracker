package com.example.na_steptracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.na_steptracker.data.prefs.settingsPrefs.SettingsPrefs
import com.example.na_steptracker.data.prefs.settingsPrefs.SettingsPrefsImpl
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsDisplayPrefs
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefs
import com.example.na_steptracker.data.prefs.stepsPrefs.StepsPrefsImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings_store")
private val Context.stepsDataStore by preferencesDataStore(name = "steps_store")

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StepsDataStore

@Module
@InstallIn(SingletonComponent::class)
abstract class PrefsModule {

    @Binds
    @Singleton
    abstract fun bindSettingsPrefs(impl: SettingsPrefsImpl): SettingsPrefs

    @Binds
    @Singleton
    abstract fun bindStepsPrefs(impl: StepsPrefsImpl): StepsPrefs

    @Binds
    @Singleton
    abstract fun bindStepsDisplayPrefs(impl: StepsPrefsImpl): StepsDisplayPrefs

    companion object {
        @Provides
        @Singleton
        fun provideSettingsDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.settingsDataStore

        @Provides
        @Singleton
        @StepsDataStore
        fun provideStepsDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> = context.stepsDataStore
    }
}
