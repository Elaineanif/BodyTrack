package com.leaf3stones.tracker.data

data class User(
    val username:String?,
    val password:String?,
    val isAdmin:Boolean?,
    val id:Int? = null)