package com.inkapplications.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * A static implementation of a Clock.
 *
 * This is primarily useful to simplify syntax in tests.
 */
data class FixedClock(
    private val fixedTime: Instant,
): Clock {
    override fun now(): Instant = fixedTime
}
