package com.inkapplications.datetime

import kotlinx.datetime.*

/**
 * A [LocalDate] associated with a specific [TimeZone].
 */
data class ZonedDate(
    val localDate: LocalDate,
    val zone: TimeZone,
) {
    /**
     * The year component of this date for its zone.
     */
    val year get() = localDate.year

    /**
     * The month component of this date for its zone, as a [Month].
     */
    val month get() = localDate.month

    /**
     * The month component of this date for its zone, as a number.
     */
    val monthNumber get() = localDate.monthNumber

    /**
     * The day of the year for this date for its zone.
     */
    val dayOfYear get() = localDate.dayOfYear

    /**
     * The day of the month for this date for its zone.
     */
    val dayOfMonth get() = localDate.dayOfMonth

    /**
     * The day of the week for this date for its zone.
     */
    val dayOfWeek get() = localDate.dayOfWeek

    /**
     * Number of days since the Unix epoch that represents this date.
     */
    val epochDays get() = localDate.toEpochDays()

    /**
     * Associate this local date with a specific time of day.
     */
    fun atTime(time: LocalTime) = ZonedDateTime(
        localDateTime = localDate.atTime(time),
        zone = zone
    )

    /**
     * Associate this local date with a specific time of day.
     */
    fun atTime(hour: Int, minute: Int, second: Int = 0, nanosecond: Int = 0): ZonedDateTime {
        return atTime(
            LocalTime(
                hour = hour,
                minute = minute,
                second = second,
                nanosecond = nanosecond,
            )
        )
    }

    /**
     * Calculate the number of days between this date and another.
     */
    fun daysUntil(other: LocalDate) = localDate.daysUntil(other)

    /**
     * Calculate the number of months between this date and another.
     */
    fun monthsUntil(other: LocalDate) = localDate.monthsUntil(other)

    /**
     * Calculate the number of years between this date and another.
     */
    fun yearsUntil(other: LocalDate) = localDate.yearsUntil(other)

    /**
     * Calculate the period between this date and another.
     */
    fun periodUntil(other: LocalDate) = localDate.periodUntil(other)

    /**
     * Calculate the difference between this date and another in a specified unit.
     */
    fun until(other: LocalDate, unit: DateTimeUnit.DateBased) = localDate.until(other, unit)

    /**
     * Add a period of time to this date.
     */
    operator fun plus(other: DatePeriod) = localDate.plus(other).atZone(zone)

    /**
     * Add a period of time to this date.
     */
    fun plus(value: Int, unit: DateTimeUnit.DateBased) = localDate.plus(value, unit).atZone(zone)

    /**
     * Add a period of time to this date.
     */
    fun plus(value: Long, unit: DateTimeUnit.DateBased) = localDate.plus(value, unit).atZone(zone)

    /**
     * Subtract a period of time from this date.
     */
    operator fun minus(other: DatePeriod) = localDate.minus(other).atZone(zone)

    /**
     * Subtract a period of time from this date.
     */
    fun minus(value: Int, unit: DateTimeUnit.DateBased) = localDate.minus(value, unit).atZone(zone)

    /**
     * Subtract a period of time from this date.
     */
    fun minus(value: Long, unit: DateTimeUnit.DateBased) = localDate.minus(value, unit).atZone(zone)

    /**
     * Compare this date to another local date.
     */
    operator fun compareTo(other: LocalDate): Int {
        return localDate.compareTo(other)
    }

    override fun toString(): String {
        return "$localDate $zone"
    }
}

/**
 * Associate a [LocalDate] with a [TimeZone].
 */
fun LocalDate.atZone(zone: TimeZone) = ZonedDate(
    localDate = this,
    zone = zone,
)
