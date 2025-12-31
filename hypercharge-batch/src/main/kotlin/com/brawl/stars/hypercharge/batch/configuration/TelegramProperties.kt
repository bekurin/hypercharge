package com.brawl.stars.hypercharge.batch.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "telegram")
data class TelegramProperties(
    val botToken: String,
    val chatId: String,
    val enabled: Boolean = true,
)
