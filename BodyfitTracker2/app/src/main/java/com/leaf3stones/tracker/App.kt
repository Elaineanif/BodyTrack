package com.leaf3stones.tracker

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        App.context = this
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getAppContext() :Context {
            return this.context
        }


    }
}