package com.inkapplications.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * A [Clock] associated with a specific [TimeZone].
 *
 * Unlike a regular clock, this clock is capable of producing
 * local date and time objects, that are associated with the
 * zone provided.
 */
data class ZonedClock(
    private val clock: Clock,
    val zone: TimeZone
): Clock by clock {
    /**
     * Get the current local date time in this clock's timezone.
     */
    fun localDateTime() = clock.now().toLocalDateTime(zone)

    /**
     * Get the current zoned date time for this clock.
     */
    fun zonedDateTime() = localDateTime().atZone(zone)

    /**
     * Get the current zoned date for this clock.
     */
    fun zonedDate() = zonedDateTime().zonedDate

    /**
     * Get the current local date in this clock's timezone.
     */
    fun localDate() = localDateTime().date

    /**
     * Get the current zoned time for this clock.
     */
    fun zonedTime() = zonedDateTime().zonedTime

    /**
     * Get the current local time in this clock's timezone.
     */
    fun localTime() = localDateTime().time

    companion object {
        /**
         * The System clock in the default system's timezone.
         */
        val System = Clock.System.atZone(TimeZone.currentSystemDefault())

        /**
         * The System clock in UTC.
         */
        val UTC = Clock.System.atZone(TimeZone.UTC)
    }
}

/**
 * Associate this clock with a particular timezone.
 */
fun Clock.atZone(zone: TimeZone) = ZonedClock(this, zone)
