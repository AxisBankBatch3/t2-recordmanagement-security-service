package com.delta.security.service

import com.delta.security.model.User
import com.delta.security.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AccessServiceImpl : AccessService {
    @Autowired
    lateinit var userRepository: UserRepository

    private val logger: Logger = LoggerFactory.getLogger(AccessServiceImpl::class.java)

    @Autowired
    lateinit var encoder: PasswordEncoder

    override fun getAllDetails(): MutableList<User> {
        return userRepository!!.findAll()
    }

     override fun getByUsername(username: String): User {
         val emptyUser = User()
         emptyUser.username="null"
         return if(username.isEmpty()){
             emptyUser
         } else{
             userRepository!!.findByUsername(username)
         }
    }

    override fun existByUsername(emailId: String): Boolean {
        return if(emailId.isEmpty()){
            false
        } else{
            userRepository!!.existsByUsername(emailId)
        }
    }

    override fun existUserByMobileNumber(mobile: String): Boolean{
        return if(mobile.isEmpty()){
            false
        }
        else {
            return userRepository!!.existsByMobile(mobile)
        }
    }

    override fun findByMobile(mobile: String): User {
        val emptyUser = User()
        emptyUser.username="null"
        return if(mobile.isEmpty()){
            emptyUser
        } else {
            return userRepository!!.findByMobile(mobile)
        }
    }



    override fun signUp(user: User): String {
        logger.info("adding new partner")
        var newUser = User(user.fullName,
            user.organization,
            user.username,
            user.mobile,
            encoder.encode(user.password),
            user.isAdmin)
        userRepository.save(newUser)
        logger.info("new partner added")
        println(newUser)
        val response = "user added successfully"
        return response
    }
}
