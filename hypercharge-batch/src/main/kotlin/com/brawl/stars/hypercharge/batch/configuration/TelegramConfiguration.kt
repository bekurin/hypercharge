package com.brawl.stars.hypercharge.batch.configuration

import com.brawl.stars.hypercharge.batch.client.TelegramApiClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
@EnableConfigurationProperties(TelegramProperties::class)
class TelegramConfiguration {
    @Bean
    fun telegramApiClient(): TelegramApiClient {
        val restClient =
            RestClient
                .builder()
                .baseUrl("https://api.telegram.org")
                .build()

        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(TelegramApiClient::class.java)
    }
}
