package com.inkapplications.coroutines

import com.inkapplications.standard.CompositeException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration

/**
 * Execute a block of code, retrying failures.
 *
 * @param attemptTimeout Maximum amount of time to wait for the task to complete.
 * @param strategy Strategy to use retry attempts if the task fails.
 * @param retryFilter A filter to use before attempting a retry. The method will
 *        throw if this filter condition is not satisfied.
 * @param onError invoked each time the attempt fails to complete.
 * @param block The task to be executed/retried.
 */
suspend fun <T> runRetryable(
    attemptTimeout: Duration = Duration.INFINITE,
    strategy: RetryStrategy,
    retryFilter: (Throwable) -> Boolean = { it !is CancellationException || it is TimeoutCancellationException },
    onError: suspend (Throwable) -> Unit = {},
    block: suspend () -> T,
): Result<T> {
    val timeoutBlock: suspend () -> T = {
        withTimeout(attemptTimeout) {
            block()
        }
    }
    return runCatching {
        val errors: MutableList<Throwable> = mutableListOf()
        if (strategy.attempts == 0) {
            throw IllegalArgumentException("No attempts were made.")
        }
        repeat(strategy.attempts) { attempt ->
            try {
                return@runCatching timeoutBlock()
            } catch (e: Throwable) {
                errors += e
                onError(e)
                if (attempt >= strategy.attempts - 1) throw wrapErrors(errors)
                if (!retryFilter(e)) throw wrapErrors(errors)
                delay(strategy.calculateDelay(attempt))
            }
        }
        throw IllegalStateException("Repeat completed before retry conditions satisfied")
    }
}

/**
 * Wrap a list of throwables in a single composite, if necessary.
 */
private fun wrapErrors(exceptions: List<Throwable>): Throwable {
    return when (exceptions.size) {
        0 -> IllegalStateException("exceptions is empty")
        1 -> exceptions.first()
        else -> CompositeException(exceptions)
    }
}
