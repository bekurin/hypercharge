package com.brawl.stars.hypercharge.batch.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "brawlstars.api")
data class BrawlStarsApiProperties(
    val baseUrl: String,
    val key: String,
)
