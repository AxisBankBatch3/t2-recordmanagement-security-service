package com.delta.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@SpringBootApplication
class SecurityApplication

var log: Logger = LoggerFactory.getLogger(SecurityApplication::class.java)


fun main(args: Array<String>) {
	runApplication<SecurityApplication>(*args)
	log.info("Security Application started")
}
