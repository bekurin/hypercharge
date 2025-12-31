package com.brawl.stars.hypercharge.batch.service

import com.brawl.stars.hypercharge.batch.client.TelegramApiClient
import com.brawl.stars.hypercharge.batch.configuration.TelegramProperties
import org.slf4j.LoggerFactory
import org.springframework.batch.core.job.JobExecution
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class TelegramNotificationService(
    private val telegramApiClient: TelegramApiClient,
    private val telegramProperties: TelegramProperties,
) {
    private val log = LoggerFactory.getLogger(TelegramNotificationService::class.java)

    fun sendJobFailureNotification(jobExecution: JobExecution) {
        if (!telegramProperties.enabled) {
            log.debug("Telegram notification is disabled")
            return
        }

        val message = buildFailureMessage(jobExecution)
        sendMessage(message)
    }

    private fun buildFailureMessage(jobExecution: JobExecution): String {
        val jobName = jobExecution.jobInstance.jobName
        val failedTime = jobExecution.endTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ?: "Unknown"
        val errorMessage = extractErrorMessage(jobExecution)

        return """
            |⚠️ [Hypercharge] 배치 작업 실패 알림
            |------------------------------------
            |- 작업명: $jobName
            |- 발생 시각: $failedTime
            |- 에러 내용: $errorMessage
            |- 상태: ${jobExecution.status}
            |------------------------------------
            |로그를 확인하여 조치해 주세요.
            """.trimMargin()
    }

    private fun extractErrorMessage(jobExecution: JobExecution): String {
        val stepExceptions =
            jobExecution.stepExecutions
                .filter { it.failureExceptions.isNotEmpty() }
                .flatMap { it.failureExceptions }

        if (stepExceptions.isNotEmpty()) {
            return stepExceptions.first().message ?: "Unknown error"
        }

        return jobExecution.exitStatus.exitDescription.ifBlank { "Unknown error" }
    }

    private fun sendMessage(message: String) {
        try {
            telegramApiClient.sendMessage(
                botToken = telegramProperties.botToken,
                chatId = telegramProperties.chatId,
                text = message,
            )
            log.info("Telegram notification sent successfully")
        } catch (e: Exception) {
            log.error("Failed to send Telegram notification: {}", e.message)
        }
    }
}
