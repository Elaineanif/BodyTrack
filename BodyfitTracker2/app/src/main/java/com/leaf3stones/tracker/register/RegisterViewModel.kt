package com.leaf3stones.tracker.register

import androidx.lifecycle.ViewModel
import com.leaf3stones.tracker.data.User
import com.leaf3stones.tracker.data.UserRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel : ViewModel() {
    private val userRepository = UserRepository()

    var user = User(null , null, false)

    var id :Int? = null

    fun updateAdmin(boolean: Boolean) {
        user = user.copy(isAdmin = boolean)
    }

    fun updateUsername(username: String?) {
        user = user.copy(username = username)
    }

    fun updatePassword(password: String?) {
        user = user.copy(password = password)
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun registerUser(): Boolean {

        var isSuccess = false
        return if (checkFields()) {
            runBlocking {
                val job = launch {
                    val searchUser = userRepository.getUserByUsername(username = user.username!!)
                    if (searchUser == null) {
                        // username not taken
                        isSuccess = true
                        userRepository.addUser(user)
                        val newAddedUser = userRepository.getUserByUsername(username = user.username!!)
                        id = newAddedUser!!.id
                    }
                }
                job.join()
            }
            isSuccess
        } else
        {
            // not filled all fields
            isSuccess
        }
    }

    fun checkFields() = !user.username.isNullOrEmpty() && !user.password.isNullOrEmpty()

}