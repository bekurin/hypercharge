package com.brawl.stars.hypercharge.batch.job

import com.brawl.stars.hypercharge.batch.job.listener.JobExecutionLoggingListener
import com.brawl.stars.hypercharge.batch.job.listener.StepExecutionLoggingListener
import com.brawl.stars.hypercharge.batch.job.processor.BrawlerStatsProcessor
import com.brawl.stars.hypercharge.batch.job.processor.CombinationStatsProcessor
import com.brawl.stars.hypercharge.batch.job.tasklet.ClearStatTablesTasklet
import com.brawl.stars.hypercharge.batch.job.writer.BrawlerStatsWriter
import com.brawl.stars.hypercharge.batch.job.writer.CombinationStatsWriter
import com.brawl.stars.hypercharge.domain.entity.write.BattleLog
import com.brawl.stars.hypercharge.domain.entity.write.BattleParticipants
import com.brawl.stars.hypercharge.domain.repository.write.BattleLogRepository
import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.parameters.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.Step
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

@Configuration
class StatAggregationJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val battleLogRepository: BattleLogRepository,
    private val clearStatTablesTasklet: ClearStatTablesTasklet,
    private val brawlerStatsProcessor: BrawlerStatsProcessor,
    private val brawlerStatsWriter: BrawlerStatsWriter,
    private val combinationStatsProcessor: CombinationStatsProcessor,
    private val combinationStatsWriter: CombinationStatsWriter,
    private val jobExecutionLoggingListener: JobExecutionLoggingListener,
    private val stepExecutionLoggingListener: StepExecutionLoggingListener
) {
    companion object {
        private const val ROLLING_WINDOW_DAYS = 30L
        private const val CHUNK_SIZE = 100
    }

    @Bean
    fun statAggregationJob(): Job {
        return JobBuilder("statAggregationJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(jobExecutionLoggingListener)
            .start(clearStatTablesStep())
            .next(aggregateBrawlerStatsStep())
            .next(aggregateCombinationStatsStep())
            .build()
    }

    @Bean
    fun clearStatTablesStep(): Step {
        return StepBuilder(jobRepository)
            .tasklet(clearStatTablesTasklet, transactionManager)
            .listener(stepExecutionLoggingListener)
            .build()
    }

    @Bean
    fun aggregateBrawlerStatsStep(): Step {
        return StepBuilder(jobRepository)
            .chunk<BattleLog, List<BattleParticipants.BrawlerStatData>>(CHUNK_SIZE)
            .transactionManager(transactionManager)
            .reader(brawlerStatsBattleLogReader())
            .processor(brawlerStatsProcessor)
            .writer(brawlerStatsWriter)
            .listener(stepExecutionLoggingListener)
            .build()
    }

    @Bean
    fun aggregateCombinationStatsStep(): Step {
        return StepBuilder(jobRepository)
            .chunk<BattleLog, List<BattleParticipants.TeamCombinationData>>(CHUNK_SIZE)
            .transactionManager(transactionManager)
            .reader(combinationStatsBattleLogReader())
            .processor(combinationStatsProcessor)
            .writer(combinationStatsWriter)
            .listener(stepExecutionLoggingListener)
            .build()
    }

    @Bean
    fun brawlerStatsBattleLogReader(): RepositoryItemReader<BattleLog> {
        val since = LocalDateTime.now().minusDays(ROLLING_WINDOW_DAYS)

        return RepositoryItemReaderBuilder<BattleLog>()
            .name("brawlerStatsBattleLogReader")
            .repository(battleLogRepository)
            .methodName("findByBattleTimeGreaterThanEqual")
            .arguments(listOf(since))
            .sorts(mapOf("id" to Sort.Direction.ASC))
            .pageSize(CHUNK_SIZE)
            .build()
    }

    @Bean
    fun combinationStatsBattleLogReader(): RepositoryItemReader<BattleLog> {
        val since = LocalDateTime.now().minusDays(ROLLING_WINDOW_DAYS)

        return RepositoryItemReaderBuilder<BattleLog>()
            .name("combinationStatsBattleLogReader")
            .repository(battleLogRepository)
            .methodName("findByBattleTimeGreaterThanEqual")
            .arguments(listOf(since))
            .sorts(mapOf("id" to Sort.Direction.ASC))
            .pageSize(CHUNK_SIZE)
            .build()
    }
}
