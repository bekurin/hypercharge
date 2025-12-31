package com.brawl.stars.hypercharge.batch.job.tasklet

import com.brawl.stars.hypercharge.domain.repository.read.StatMapBrawlerRepository
import com.brawl.stars.hypercharge.domain.repository.read.StatMapCombinationRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.StepContribution
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.infrastructure.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ClearStatTablesTasklet(
    private val statMapBrawlerRepository: StatMapBrawlerRepository,
    private val statMapCombinationRepository: StatMapCombinationRepository,
) : Tasklet {
    private val log = LoggerFactory.getLogger(ClearStatTablesTasklet::class.java)

    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus {
        statMapBrawlerRepository.deleteAll()
        log.info("Cleared stat_map_brawler table")

        statMapCombinationRepository.deleteAll()
        log.info("Cleared stat_map_combination table")

        return RepeatStatus.FINISHED
    }
}
