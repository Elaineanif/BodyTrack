package com.leaf3stones.tracker.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leaf3stones.tracker.data.User
import com.leaf3stones.tracker.data.UserRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()

    init {
        runBlocking {
            val job = launch {
                    userRepository.addUser(User("admin", "admin", true, 0))
            }
            job.join()
        }
    }

    var user = User(null , null, false)

    fun updateUsername(username: String?) {
        user = user.copy(username = username)
    }

    fun updatePassword(password: String?) {
        user = user.copy(password = password)
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun searchUser() : User? {
        return if (!user.username.isNullOrEmpty() && !user.password.isNullOrEmpty()) {
            // sufficient info obtained from UI to perform a query
            var dbUser:User? = null
            runBlocking {
                val job = launch {
                    dbUser =  userRepository.getUserByUsername(user.username!!)
                }
                job.join()
            }
            dbUser
        } else
        {
            null
        }
    }
}