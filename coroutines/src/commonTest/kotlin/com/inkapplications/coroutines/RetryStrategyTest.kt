package com.inkapplications.coroutines

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class RetryStrategyTest {
    @Test
    fun static() {
        val strategy = RetryStrategy.Static(
            attempts = 3,
            timeout = 123.seconds,
        )

        assertEquals(3, strategy.attempts)
        assertEquals(123.seconds, strategy.calculateDelay(0))
        assertEquals(123.seconds, strategy.calculateDelay(1))
        assertEquals(123.seconds, strategy.calculateDelay(2))
    }

    @Test
    fun exponential() {
        val strategy = RetryStrategy.Exponential(
            attempts = 3,
            initial = 4.seconds,
            maximum = 10.seconds,
            factor = 2f,
        )

        assertEquals(3, strategy.attempts)
        assertEquals(4.seconds, strategy.calculateDelay(0))
        assertEquals(8.seconds, strategy.calculateDelay(1))
        assertEquals(10.seconds, strategy.calculateDelay(2))
    }

    @Test
    fun bracket() {
        val strategy = RetryStrategy.Bracket(
            attempts = 3,
            timeouts = listOf(123.seconds, 321.seconds, 1.seconds),
        )

        assertEquals(3, strategy.attempts)
        assertEquals(123.seconds, strategy.calculateDelay(0))
        assertEquals(321.seconds, strategy.calculateDelay(1))
        assertEquals(1.seconds, strategy.calculateDelay(2))
        assertEquals(1.seconds, strategy.calculateDelay(3), "Should use last item for all subsequent attempts")
    }

    @Test
    fun emptyBracket() {
        val strategy = RetryStrategy.Bracket(
            attempts = 3,
            timeouts = listOf(),
        )

        assertEquals(0.seconds, strategy.calculateDelay(0))
    }
}
