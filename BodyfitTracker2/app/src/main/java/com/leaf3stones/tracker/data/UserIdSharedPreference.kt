package com.leaf3stones.tracker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.leaf3stones.tracker.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val USER_ID = intPreferencesKey("id")

fun setUserId(id : Int) {
    runBlocking {
        val job = launch {
            App.getAppContext().dataStore.edit {settings ->
                 settings[USER_ID] = id
            }

        }
        job.join()
    }
}

fun getUserId() : Int {
    val data = runBlocking {
        App.getAppContext().dataStore.data.first()
    }
    return data[USER_ID]!!
}
