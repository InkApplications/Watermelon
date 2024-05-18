package com.inkapplications.coroutines

import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * Strategies for retrying a block of code.
 */
interface RetryStrategy {
    /**
     * The number of attempts to invoke the code.
     */
    val attempts: Int

    /**
     * Calculate a delay to wait before the next attempt.
     */
    fun calculateDelay(attempt: Int): Duration

    /**
     * Retry using a fixed timeout period between requests.
     *
     * @param attempts The number of attempts to invoke the code.
     * @param timeout Duration to wait before attempting a retry
     */
    data class Static(
        override val attempts: Int,
        val timeout: Duration
    ): RetryStrategy {
        override fun calculateDelay(attempt: Int): Duration = timeout
    }

    /**
     * Retry using an exponential backoff definition.
     *
     * @param attempts The number of attempts to invoke the code.
     * @param initial The initial delay after the first attempt fails.
     * @param maximum The upper limit of delays between attempts
     * @param factor Exponent base to use when retrying
     */
    data class Exponential(
        override val attempts: Int,
        val initial: Duration = 100.milliseconds,
        val maximum: Duration = 10.seconds,
        val factor: Float = 2f,
    ): RetryStrategy {
        override fun calculateDelay(attempt: Int): Duration {
            return (initial.inWholeMilliseconds.toDouble() * factor.pow(attempt))
                .toLong()
                .coerceAtMost(maximum.inWholeMilliseconds)
                .milliseconds
        }
    }

    /**
     * Retry using a fixed list of duration timeouts.
     *
     * This offers a way to statically customize the retry durations or define
     * an explicit exponent in an easier way than using an [Exponential] retry.
     *
     * If the number of attempts exceeds the durations defined in [timeouts],
     * the last timeout will be repeated.
     * eg. A bracket of [1.seconds, 2.seconds] repeated three times will be
     * delayed for 1 seconds, 2 seconds, and 2 seconds, respectively.
     *
     * If no timeouts are defined no delay will be made.
     *
     * @param attempts The number of attempts to invoke the code.
     * @param timeouts A list of timeouts to use, indexed by attempt.
     */
    data class Bracket(
        override val attempts: Int,
        val timeouts: List<Duration>,
    ): RetryStrategy {
        override fun calculateDelay(attempt: Int): Duration {
            return timeouts.getOrNull(attempt) ?: timeouts.lastOrNull() ?: Duration.ZERO
        }
    }
}
