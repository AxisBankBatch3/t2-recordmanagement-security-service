package com.delta.security.service

import com.delta.security.model.User

interface AccessService {
    fun getByUsername(username: String): User
    fun existByUsername(emailId: String): Boolean
    fun existUserByMobileNumber(mobile: String): Boolean
    fun findByMobile(mobile: String): User

    fun getAllDetails() : MutableList<User>

    fun signUp(user : User):String
}