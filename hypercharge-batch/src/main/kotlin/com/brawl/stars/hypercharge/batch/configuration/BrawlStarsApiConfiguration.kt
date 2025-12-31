package com.brawl.stars.hypercharge.batch.configuration

import com.brawl.stars.hypercharge.batch.client.BrawlStarsApiClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
@EnableConfigurationProperties(BrawlStarsApiProperties::class)
class BrawlStarsApiConfiguration(
    private val properties: BrawlStarsApiProperties
) {

    @Bean
    fun brawlStarsApiClient(): BrawlStarsApiClient {
        val restClient = RestClient.builder()
            .baseUrl(properties.baseUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${properties.key}")
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(BrawlStarsApiClient::class.java)
    }
}
