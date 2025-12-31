package com.brawl.stars.hypercharge.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.brawl.stars.hypercharge"])
class HyperchargeBatchApplication

fun main(args: Array<String>) {
    runApplication<HyperchargeBatchApplication>(*args)
}
