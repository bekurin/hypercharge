package com.brawl.stars.hypercharge.batch.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

interface TelegramApiClient {
    @GetExchange("/bot{botToken}/sendMessage")
    fun sendMessage(
        @PathVariable botToken: String,
        @RequestParam("chat_id") chatId: String,
        @RequestParam("text") text: String,
    )
}
