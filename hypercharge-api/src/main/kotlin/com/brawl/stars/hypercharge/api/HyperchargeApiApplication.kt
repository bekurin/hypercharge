package com.brawl.stars.hypercharge.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.brawl.stars.hypercharge"])
class HyperchargeApiApplication

fun main(args: Array<String>) {
    runApplication<HyperchargeApiApplication>(*args)
}
