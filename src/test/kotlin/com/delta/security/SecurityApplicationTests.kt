//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.context.SpringBootTest

package com.delta.security

import com.delta.security.model.User
import com.delta.security.repository.UserRepository
import com.delta.security.service.AccessService
import com.delta.security.service.UserDetailsServiceImpl
import com.delta.security.service.UserPrincipal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class SecurityApplicationTests {

    @MockBean
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var accessService: AccessService


    @Autowired
    lateinit var userDetailsServiceImpl: UserDetailsServiceImpl



    @Autowired
    lateinit var encoder: PasswordEncoder


    @Test
    fun getAllDetailsTest() {
        var data = User()

        data.fullName = "vaibhav"
        data.organization = "axis"
        data.username = "vaibhav@gmail.com"
        data.mobile = "9573631345"
        data.isAdmin = false
        Mockito.`when`(userRepository!!.findAll())
            .thenReturn(listOf(data))
        Assertions.assertEquals(1, accessService!!.getAllDetails().size);
    }

    @Test
    fun getByUsernamePassTest() {
        val user = User()
        user.fullName = "vaibhav"
        user.organization = "axis"
        user.username = "vaibhav@gmail.com"
        user.mobile = "9573631345"
        user.isAdmin = false
        val username = "vaibhav@gmail.com"
        Mockito.`when`(userRepository!!.findByUsername(username)).thenReturn(user)
        Assertions.assertEquals(user, accessService!!.getByUsername(username))
    }
    @Test
    fun getByUsernameFailTest() {
        val emptyUser = User()
        emptyUser.username = "null"
        val username = ""
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(emptyUser)
        val response = accessService.getByUsername(username)
        Assertions.assertEquals("null", response.username)
    }

    @Test
    fun existByUsernamePassTest() {
        val username = "vaibhav@gmail.com"
        Mockito.`when`(userRepository.existsByUsername(username)).thenReturn(true)
        Assertions.assertTrue(accessService!!.existByUsername(username))
    }

    @Test
    fun existByUsernameFailTest() {
        val username = ""
        val emptyUser = User()
        emptyUser.username="null"
        Assertions.assertFalse(accessService!!.existByUsername(username))
    }


   



    @Test
    fun findByMobilePassTest() {
        val user = User()
        user.fullName = "vaibhav"
        user.organization = "axis"
        user.username = "vaibhav@gmail.com"
        user.mobile = "9573631345"
        user.isAdmin = false
        val mobile = "12345"
        Mockito.`when`(userRepository.findByMobile(mobile)).thenReturn(user)
        Assertions.assertEquals(user, accessService.findByMobile(mobile))
    }

    @Test
    fun findByMobileFailTest()
    {
        val emptyUser = User()
        emptyUser.username="null"
        val mobile = ""
        Mockito.`when`(userRepository!!.findByMobile(mobile)).thenReturn(emptyUser)
        val response = accessService!!.findByMobile(mobile)
        Assertions.assertEquals("null", response.username)
    }

    @Test
    fun signUpTest() {
        val newUser = User()
        newUser.fullName = "test User"
        newUser.isAdmin = true
        newUser.username = "testUser"
        newUser.mobile = "12345"
        newUser.organization = "deltaBank"
        newUser.id = "0001"
        newUser.password = encoder.encode("nl3fao3i7h9wh94g")

        val response = "user added successfully"

        Mockito.`when`(userRepository.save(newUser)).thenReturn(newUser)
            Assertions.assertEquals(response, accessService.signUp(newUser))
    }

    @Test
    fun loadUserByUsernameTest() {
        val user = User()
        user.fullName = "vaibhav"
        user.organization = "axis"
        user.username = "vaibhav@gmail.com"
        user.mobile = "9573631345"
        user.isAdmin = false
        val username = "vaibhav@gmail.com"
        Mockito.`when`(userRepository.findByUsername(username)).thenReturn(user)
        Assertions.assertEquals(UserPrincipal.build(user), userDetailsServiceImpl.loadUserByUsername(username))
    }



}