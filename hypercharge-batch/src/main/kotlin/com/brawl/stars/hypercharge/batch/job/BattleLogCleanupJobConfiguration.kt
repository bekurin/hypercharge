package com.brawl.stars.hypercharge.batch.job

import com.brawl.stars.hypercharge.batch.job.listener.JobExecutionLoggingListener
import com.brawl.stars.hypercharge.batch.job.listener.StepExecutionLoggingListener
import com.brawl.stars.hypercharge.domain.repository.write.BattleLogRepository
import com.brawl.stars.hypercharge.domain.repository.write.BattleParticipantDetailRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.parameters.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.Step
import org.springframework.batch.core.step.StepContribution
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.infrastructure.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

@Configuration
class BattleLogCleanupJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val battleLogRepository: BattleLogRepository,
    private val battleParticipantDetailRepository: BattleParticipantDetailRepository,
    private val jobExecutionLoggingListener: JobExecutionLoggingListener,
    private val stepExecutionLoggingListener: StepExecutionLoggingListener
) {
    private val log = LoggerFactory.getLogger(BattleLogCleanupJobConfiguration::class.java)

    companion object {
        private const val RETENTION_YEARS = 1L
    }

    @Bean
    fun battleLogCleanupJob(): Job {
        return JobBuilder("battleLogCleanupJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .listener(jobExecutionLoggingListener)
            .start(deleteParticipantDetailsStep())
            .next(deleteBattleLogsStep())
            .build()
    }

    @Bean
    fun deleteParticipantDetailsStep(): Step {
        return StepBuilder(jobRepository)
            .tasklet(deleteParticipantDetailsTasklet(), transactionManager)
            .listener(stepExecutionLoggingListener)
            .build()
    }

    @Bean
    fun deleteBattleLogsStep(): Step {
        return StepBuilder(jobRepository)
            .tasklet(deleteBattleLogsTasklet(), transactionManager)
            .listener(stepExecutionLoggingListener)
            .build()
    }

    @Bean
    fun deleteParticipantDetailsTasklet(): Tasklet {
        return Tasklet { _: StepContribution, _: ChunkContext ->
            val cutoffDate = LocalDateTime.now().minusYears(RETENTION_YEARS)
            val deletedCount = battleParticipantDetailRepository.deleteByBattleLogBattleTimeBefore(cutoffDate)
            log.info("Deleted {} participant details older than {}", deletedCount, cutoffDate)
            RepeatStatus.FINISHED
        }
    }

    @Bean
    fun deleteBattleLogsTasklet(): Tasklet {
        return Tasklet { _: StepContribution, _: ChunkContext ->
            val cutoffDate = LocalDateTime.now().minusYears(RETENTION_YEARS)
            val targetCount = battleLogRepository.countByBattleTimeBefore(cutoffDate)
            log.info("Found {} battle logs older than {} to delete", targetCount, cutoffDate)

            val deletedCount = battleLogRepository.deleteByBattleTimeBefore(cutoffDate)
            log.info("Deleted {} battle logs older than {}", deletedCount, cutoffDate)
            RepeatStatus.FINISHED
        }
    }
}
