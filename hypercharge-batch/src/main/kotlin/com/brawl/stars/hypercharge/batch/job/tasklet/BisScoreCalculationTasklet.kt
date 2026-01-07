package com.brawl.stars.hypercharge.batch.job.tasklet

import com.brawl.stars.hypercharge.domain.entity.read.StatMapBrawlers
import com.brawl.stars.hypercharge.domain.repository.read.StatMapBrawlerRepository
import org.slf4j.LoggerFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.StepContribution
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.infrastructure.repeat.RepeatStatus
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component
class BisScoreCalculationTasklet(
    private val statMapBrawlerRepository: StatMapBrawlerRepository,
) : Tasklet {
    private val log = LoggerFactory.getLogger(BisScoreCalculationTasklet::class.java)

    companion object {
        private val WIN_RATE_WEIGHT = BigDecimal("0.7")
        private val STAR_RATE_WEIGHT = BigDecimal("0.2")
        private val PICK_RATE_WEIGHT = BigDecimal("0.1")
        private const val MINIMUM_PICK_COUNT = 30L
    }

    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus {
        val allBrawlers = statMapBrawlerRepository.findAll()
        val brawlersByMapId = allBrawlers.groupBy { it.mapId }

        log.info("Calculating BIS scores for {} maps, {} brawler entries", brawlersByMapId.size, allBrawlers.size)

        brawlersByMapId.forEach { (mapId, brawlers) ->
            val statMapBrawlers = StatMapBrawlers(brawlers)
            val calculatedStats = statMapBrawlers.calculateStats()

            calculatedStats.forEach { stats ->
                val bisScore =
                    calculateBisScore(
                        totalPick = stats.brawler.totalPick,
                        winRate = calculateWinRate(stats.brawler.totalWin, stats.brawler.totalPick),
                        pickRate = stats.pickRate,
                        starRate = stats.starRate,
                    )
                stats.brawler.updateBisScore(bisScore)
            }

            log.debug("Calculated BIS scores for map {}: {} brawlers", mapId, brawlers.size)
        }

        statMapBrawlerRepository.saveAll(allBrawlers)
        log.info("Saved BIS scores for {} brawler entries", allBrawlers.size)

        return RepeatStatus.FINISHED
    }

    private fun calculateWinRate(
        totalWin: Long,
        totalPick: Long,
    ): BigDecimal {
        if (totalPick <= 0) return BigDecimal.ZERO
        return BigDecimal(totalWin * 100)
            .divide(BigDecimal(totalPick), 2, RoundingMode.HALF_UP)
    }

    private fun calculateBisScore(
        totalPick: Long,
        winRate: BigDecimal,
        pickRate: BigDecimal,
        starRate: BigDecimal,
    ): BigDecimal {
        if (totalPick <= MINIMUM_PICK_COUNT) {
            return BigDecimal.ZERO
        }

        return winRate
            .multiply(WIN_RATE_WEIGHT)
            .add(pickRate.multiply(PICK_RATE_WEIGHT))
            .add(starRate.multiply(STAR_RATE_WEIGHT))
            .setScale(2, RoundingMode.HALF_UP)
    }
}
