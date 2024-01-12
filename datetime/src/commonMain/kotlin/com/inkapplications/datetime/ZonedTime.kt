package com.inkapplications.datetime

import kotlinx.datetime.*

/**
 * A [LocalTime] associated with a specific [TimeZone].
 */
data class ZonedTime(
    val localTime: LocalTime,
    val zone: TimeZone,
) {
    /**
     * The hour component of this time for its zone.
     */
    val hour get() = localTime.hour

    /**
     * The minute component of this time for its zone.
     */
    val minute get() = localTime.minute

    /**
     * The second component of this time for its zone.
     */
    val second get() = localTime.second

    /**
     * The millisecond component of this time for its zone.
     */
    val nanosecond get() = localTime.nanosecond

    /**
     * The millisecond component of this time for its zone.
     */
    val secondOfDay get() = localTime.toSecondOfDay()

    /**
     * The millisecond component of this time for its zone.
     */
    val millisecondOfDay get() = localTime.toMillisecondOfDay()

    /**
     * The microsecond component of this time for its zone.
     */
    val nanosecondOfDay get() = localTime.toNanosecondOfDay()

    /**
     * Associate this local time with a specific date.
     */
    fun atDate(date: LocalDate) = ZonedDateTime(
        localDateTime = localTime.atDate(date),
        zone = zone
    )

    /**
     * Associate this local time with a specific date.
     */
    fun atDate(year: Int, monthNumber: Int, dayOfMonth: Int = 0) = atDate(
        date = LocalDate(
            year = year,
            monthNumber = monthNumber,
            dayOfMonth = dayOfMonth,
        )
    )

    /**
     * Associate this local time with a specific date.
     */
    fun atDate(year: Int, month: Month, dayOfMonth: Int) = atDate(
        date = LocalDate(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth,
        )
    )

    /**
     * Compare this time to another local time.
     */
    operator fun compareTo(other: LocalTime): Int {
        return localTime.compareTo(other)
    }

    override fun toString(): String {
        return "$localTime $zone"
    }
}

/**
 * Associate this local time with a specific [TimeZone].
 */
fun LocalTime.atZone(zone: TimeZone) = ZonedTime(
    localTime = this,
    zone = zone,
)
