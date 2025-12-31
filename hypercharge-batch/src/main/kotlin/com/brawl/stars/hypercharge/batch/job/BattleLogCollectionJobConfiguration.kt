package com.brawl.stars.hypercharge.batch.job

import com.brawl.stars.hypercharge.batch.client.BrawlStarsApiClient
import com.brawl.stars.hypercharge.batch.dto.RankedPlayer
import com.brawl.stars.hypercharge.batch.job.listener.JobExecutionLoggingListener
import com.brawl.stars.hypercharge.batch.job.listener.StepExecutionLoggingListener
import com.brawl.stars.hypercharge.batch.job.processor.BattleLogProcessor
import com.brawl.stars.hypercharge.batch.job.writer.BattleLogWriter
import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.parameters.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.Step
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.infrastructure.item.ItemReader
import org.springframework.batch.infrastructure.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class BattleLogCollectionJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val brawlStarsApiClient: BrawlStarsApiClient,
    private val battleLogProcessor: BattleLogProcessor,
    private val battleLogWriter: BattleLogWriter,
    private val jobExecutionLoggingListener: JobExecutionLoggingListener,
    private val stepExecutionLoggingListener: StepExecutionLoggingListener
) {

    @Bean
    fun battleLogCollectionJob(): Job {
        return JobBuilder("battleLogCollectionJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(jobExecutionLoggingListener)
            .start(collectBattleLogsStep())
            .build()
    }

    @Bean
    fun collectBattleLogsStep(): Step {
        return StepBuilder(jobRepository)
            .chunk<RankedPlayer, List<ProcessedBattleData>>(10)
            .transactionManager(transactionManager)
            .reader(rankedPlayerReader())
            .processor(battleLogProcessor)
            .writer(battleLogWriter)
            .listener(stepExecutionLoggingListener)
            .build()
    }

    @Bean
    fun rankedPlayerReader(): ItemReader<RankedPlayer> {
        val rankings = brawlStarsApiClient.getGlobalRankings("global")
        return ListItemReader(rankings.items.take(200))
    }
}
