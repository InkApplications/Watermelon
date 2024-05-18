package com.inkapplications.coroutines

import com.inkapplications.standard.CompositeException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RetriesTest {
    @Test
    fun basicRetry() = runTest {
        val testStrategy = RetryStrategySpy(
            attempts = 3,
            delay = 123.seconds,
        )

        val result = async {
            runRetryable(
                strategy = testStrategy,
            ) {
                throw Exception("Test")
            }
        }
        assertEquals(0, testStrategy.calculations.size, "Should wait for delay")
        advanceTimeBy(123.seconds)
        assertEquals(1, testStrategy.calculations.size, "Attempt should start after delay")
        assertEquals(0, testStrategy.calculations.first(), "Attempt calculation should be provided index of retry attempt")
        advanceUntilIdle()
        assertEquals(2, testStrategy.calculations.size, "Should retry specified times")
        assertEquals(1, testStrategy.calculations[1], "Attempt calculation should be provided index of retry attempt")
        assertTrue(result.await().isFailure)
        val resultFailure = result.await().exceptionOrNull()
        assertTrue(resultFailure is CompositeException, "Multiple exceptions should be wrapped in composite")
        assertEquals(3, resultFailure.exceptions.size, "Should have 3 exceptions")
        assertEquals("Test", (resultFailure.exceptions[0] as? Exception)?.message, "Exception should be passed through")
        assertEquals("Test", (resultFailure.exceptions[1] as? Exception)?.message, "Exception should be passed through")
        assertEquals("Test", (resultFailure.exceptions[2] as? Exception)?.message, "Exception should be passed through")
    }

    @Test
    fun singleError() = runTest {
        val testStrategy = RetryStrategySpy(
            attempts = 1,
            delay = 123.seconds,
        )

        val result = async {
            runRetryable(
                strategy = testStrategy,
            ) {
                throw Exception("Test")
            }
        }

        advanceUntilIdle()
        assertTrue(result.await().isFailure, "Job should fail")
        assertTrue(result.await().exceptionOrNull() is Exception, "Exception should be passed through, without composite")
    }

    @Test
    fun timeout() = runTest {
        val testStrategy = RetryStrategySpy(
            attempts = 2,
            delay = 123.seconds,
        )

        val result = async {
            runRetryable(
                attemptTimeout = 5.seconds,
                strategy = testStrategy,
            ) {
                delay(10.seconds)
            }
        }
        assertEquals(0, testStrategy.calculations.size, "Should wait for delay")
        advanceTimeBy(5.seconds + 123.seconds)
        assertEquals(1, testStrategy.calculations.size, "New attempt should start after timeout + delay")
        advanceUntilIdle()
        assertTrue(result.await().isFailure, "Job should fail due to timeout")
        val failure = result.await().exceptionOrNull()
        assertTrue(failure is CompositeException, "Failures should be wrapped in composite")
        assertTrue(failure.exceptions[0] is TimeoutCancellationException, "Timeout exception is passed through")
        assertTrue(failure.exceptions[1] is TimeoutCancellationException, "Timeout exception is passed through")
    }

    @Test
    fun retryFilters() = runTest {
        val testStrategy = RetryStrategySpy(
            attempts = 2,
            delay = 123.seconds,
        )

        val result = async {
            runRetryable(
                strategy = testStrategy,
                retryFilter = { it.message != "Test" },
            ) {
                throw Exception("Test")
            }
        }
        advanceUntilIdle()
        assertEquals(0, testStrategy.calculations.size, "No retries should be made")
        assertTrue(result.await().isFailure, "Job should fail")
    }

    @Test
    fun onErrorCallbacks() = runTest {
        val testStrategy = RetryStrategySpy(
            attempts = 2,
            delay = 123.seconds,
        )
        val onErrorCalls = mutableListOf<Throwable>()

        async {
            runRetryable(
                strategy = testStrategy,
                onError = { onErrorCalls.add(it) },
            ) {
                throw Exception("Test")
            }
        }
        advanceUntilIdle()
        assertEquals(2, onErrorCalls.size, "Retry should invoke onError")
        assertEquals("Test", (onErrorCalls[0] as? Exception)?.message, "Exception is passed to onError")
        assertEquals("Test", (onErrorCalls[1] as? Exception)?.message, "Exception is passed to onError")
    }
}

class RetryStrategySpy(
    override val attempts: Int = 2,
    private val delay: Duration = 123.seconds,
): RetryStrategy {
    var calculations = mutableListOf<Int>()
    override fun calculateDelay(attempt: Int): Duration  {
        calculations.add(attempt)
        return delay
    }
}
