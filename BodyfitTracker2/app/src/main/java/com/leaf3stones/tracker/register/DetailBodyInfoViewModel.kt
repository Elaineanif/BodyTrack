package com.leaf3stones.tracker.register

import java.util.Date
import android.util.Log
import androidx.lifecycle.ViewModel
import com.leaf3stones.tracker.data.BodyInfo
import com.leaf3stones.tracker.data.UserRepository
import com.leaf3stones.tracker.data.getUserId
import com.leaf3stones.tracker.data.setUserId
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class DetailBodyInfoViewModel : ViewModel() {
    var isGetHereFromLoginFragment = false

    private val userRepository = UserRepository()

    private var bodyInfo: BodyInfo? = null

    var username: String? = null
    var id: Int? = null // need change


    var name: String? = null
    var age: Int? = null
    var weight: Float? = null
    var height: Float? = null
    var emailAddress: String? = null
    var birthday: Date? = null

    private fun checkFields(): Boolean {
        return id != null && !name.isNullOrEmpty() && age != null && weight != null && height != null && !emailAddress.isNullOrEmpty() && birthday != null
    }

    fun calculateBMI(): Float? {
        return if (weight != null && height != null && height!! != 0f) {
            weight!! / (height!! * height!!)
        } else {
            null
        }
    }

    fun getUserBodyInfo(): BodyInfo? {
        runBlocking {
            val job = launch {
                id = getUserId()
                val user = userRepository.getUserById(id!!)
                username = user!!.username
                bodyInfo = userRepository.getBodyInfoById(id!!)
                if (bodyInfo == null) {
                    isGetHereFromLoginFragment = true
                }
                weight = bodyInfo?.weight
                height = bodyInfo?.height
                name = bodyInfo?.name
                age = bodyInfo?.age
                emailAddress = bodyInfo?.emailAddress
                birthday = bodyInfo?.birthday
            }
            job.join()
        }
        return bodyInfo
    }

    fun updateBodyInfo(): Boolean {
        if (!checkFields()) {
            // not all fields are filled
            return false
        } else {
            val bodyInfo = BodyInfo(
                id = id!!,
                name = name!!,
                age = age!!,
                weight = weight!!,
                height = height!!,
                birthday = birthday!!,
                emailAddress = emailAddress!!
            )
            runBlocking {
                val job = launch {
                    userRepository.updateBodyInfo(bodyInfo)
                }
                job.join()
            }
            return true
        }
    }

}