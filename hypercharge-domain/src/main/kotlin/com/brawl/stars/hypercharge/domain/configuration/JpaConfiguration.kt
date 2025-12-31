package com.brawl.stars.hypercharge.domain.configuration

import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["com.brawl.stars.hypercharge"])
@EntityScan(basePackages = ["com.brawl.stars.hypercharge"])
class JpaConfiguration
