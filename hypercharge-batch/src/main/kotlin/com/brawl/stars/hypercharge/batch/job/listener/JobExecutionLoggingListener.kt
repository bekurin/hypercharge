package com.brawl.stars.hypercharge.batch.job.listener

import com.brawl.stars.hypercharge.batch.util.DurationFormatter
import org.slf4j.LoggerFactory
import org.springframework.batch.core.job.JobExecution
import org.springframework.batch.core.listener.JobExecutionListener
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class JobExecutionLoggingListener : JobExecutionListener {

    private val log = LoggerFactory.getLogger(JobExecutionLoggingListener::class.java)

    override fun beforeJob(jobExecution: JobExecution) {
        log.info(
            "Job [{}] started at {}",
            jobExecution.jobInstance.jobName,
            jobExecution.startTime
        )
    }

    override fun afterJob(jobExecution: JobExecution) {
        val duration = calculateDuration(jobExecution)
        log.info(
            "Job [{}] finished with status [{}] - elapsed time: {}",
            jobExecution.jobInstance.jobName,
            jobExecution.status,
            DurationFormatter.format(duration)
        )
    }

    private fun calculateDuration(jobExecution: JobExecution): Duration {
        val startTime = jobExecution.startTime ?: return Duration.ZERO
        val endTime = jobExecution.endTime ?: return Duration.ZERO
        return Duration.between(startTime, endTime)
    }
}
