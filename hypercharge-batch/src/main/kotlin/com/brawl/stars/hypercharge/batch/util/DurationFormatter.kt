package com.brawl.stars.hypercharge.batch.util

import java.time.Duration

object DurationFormatter {

    fun format(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutesPart()
        val seconds = duration.toSecondsPart()
        val millis = duration.toMillisPart()

        return when {
            hours > 0 -> "${hours}h ${minutes}m ${seconds}s"
            minutes > 0 -> "${minutes}m ${seconds}s"
            seconds > 0 -> "${seconds}s ${millis}ms"
            else -> "${millis}ms"
        }
    }
}
