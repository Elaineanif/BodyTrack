package com.leaf3stones.tracker.mainpage

import androidx.lifecycle.ViewModel

class BmiCalculateViewModel : ViewModel() {
    var weight: Float? = null
    var height: Float? = null

    fun calculateBMI(): Float? {
        return if (weight != null && height != null && height!! != 0f) {
            weight!! / (height!! * height!!)
        } else {
            null
        }
    }
}