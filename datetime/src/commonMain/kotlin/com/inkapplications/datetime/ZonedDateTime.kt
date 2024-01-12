package com.inkapplications.datetime

import kotlinx.datetime.*
import kotlin.time.Duration

/**
 * A [LocalDateTime] associated with a specific [TimeZone].
 */
data class ZonedDateTime(
    val localDateTime: LocalDateTime,
    val zone: TimeZone,
): Comparable<ZonedDateTime> {
    /**
     * The [Instant] that corresponds to the time in this zone.
     */
    val instant get() = localDateTime.toInstant(zone)

    /**
     * The local date portion of this date-time for its zone.
     */
    val localDate get() = localDateTime.date

    /**
     * The local time portion of this date-time for its zone.
     */
    val localTime get() = localDateTime.time

    /**
     * The local date associated with the current zone.
     */
    val zonedDate get() = localDate.atZone(zone)

    /**
     * The local time associated with the current zone.
     */
    val zonedTime get() = localTime.atZone(zone)

    /**
     * The year component of this date for its zone.
     */
    val year get() = localDateTime.year

    /**
     * The month component of this date for its zone, as a [Month].
     */
    val month get() = localDateTime.month

    /**
     * The month component of this date for its zone, as a number.
     */
    val monthNumber get() = localDateTime.monthNumber

    /**
     * The day of the year for this date for its zone.
     */
    val dayOfYear get() = localDateTime.dayOfYear

    /**
     * The day of the month for this date for its zone.
     */
    val dayOfMonth get() = localDateTime.dayOfMonth

    /**
     * The day of the week for this date for its zone.
     */
    val dayOfWeek get() = localDateTime.dayOfWeek

    /**
     * The hour component of this time for its zone.
     */
    val hour get() = localDateTime.hour

    /**
     * The minute component of this time for its zone.
     */
    val minute get() = localDateTime.minute

    /**
     * The second component of this time for its zone.
     */
    val second get() = localDateTime.second

    /**
     * The nanosecond component of this time for its zone.
     */
    val nanosecond get() = localDateTime.nanosecond

    /**
     * Increase the current date-time by the given [DateTimePeriod].
     */
    operator fun plus(period: DateTimePeriod) = instant.plus(period, zone)
        .toLocalDateTime(zone)
        .atZone(zone)

    /**
     * Increase the current date-time by the given [Duration].
     */
    operator fun plus(duration: Duration) = instant.plus(duration)
        .toLocalDateTime(zone)
        .atZone(zone)

    /**
     * Decrease the current date-time by the given [DateTimePeriod].
     */
    operator fun minus(period: DateTimePeriod) = instant.minus(period, zone)
        .toLocalDateTime(zone)
        .atZone(zone)

    /**
     * Decrease the current date-time by the given [Duration].
     */
    operator fun minus(duration: Duration) = instant.minus(duration)
        .toLocalDateTime(zone)
        .atZone(zone)

    /**
     * Compare the instants of two date-times in their zone.
     */
    override operator fun compareTo(other: ZonedDateTime): Int {
        return instant.compareTo(other.instant)
    }

    override fun toString(): String {
        return "$localDateTime $zone"
    }
}

/**
 * Create a [ZonedDateTime] from a [LocalDateTime] and [TimeZone].
 */
fun LocalDateTime.atZone(zone: TimeZone) = ZonedDateTime(
    localDateTime = this,
    zone = zone,
)
