package com.brawl.stars.hypercharge.batch.job.listener

import com.brawl.stars.hypercharge.batch.util.DurationFormatter
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.listener.StepExecutionListener
import org.springframework.batch.core.step.StepExecution
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class StepExecutionLoggingListener : StepExecutionListener {
    private val log = LoggerFactory.getLogger(StepExecutionLoggingListener::class.java)

    override fun beforeStep(stepExecution: StepExecution) {
        log.info(
            "Step [{}] started at {}",
            stepExecution.stepName,
            stepExecution.startTime,
        )
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus {
        val duration = calculateDuration(stepExecution)
        log.info(
            "Step [{}] finished with status [{}] - read: {}, written: {}, skipped: {} - elapsed time: {}",
            stepExecution.stepName,
            stepExecution.status,
            stepExecution.readCount,
            stepExecution.writeCount,
            stepExecution.skipCount,
            DurationFormatter.format(duration),
        )
        return stepExecution.exitStatus
    }

    private fun calculateDuration(stepExecution: StepExecution): Duration {
        val startTime = stepExecution.startTime ?: return Duration.ZERO
        val endTime = stepExecution.endTime ?: return Duration.ZERO
        return Duration.between(startTime, endTime)
    }
}
