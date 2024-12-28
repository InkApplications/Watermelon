package com.inkapplications.coroutines.ongoing

import com.inkapplications.coroutines.doubles.Animal
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

class CollectorsTest
{
    @Test
    fun collect()
    {
        runTest {
            val flow = channelFlow {
                send(Animal.Bird)
                awaitClose {}
            }
            val ongoing = flow.asOngoing()
            val collected = MutableSharedFlow<Animal>()

            val result = async { collected.first() }
            val job = launch { ongoing.collect { collected.emit(it) } }

            assertEquals(Animal.Bird, result.await(), "Collected item is emitted")
            job.cancelAndJoin()
        }
    }

    @Test
    fun collectLatest()
    {
        runTest {
            val flow = channelFlow {
                send(Animal.Bird)
                delay(1.minutes)
                send(Animal.Cat)
                awaitClose {}
            }
            val ongoing = flow.asOngoing()
            val collected = MutableSharedFlow<Animal>()

            val result = async { collected.first() }
            val job = launch {
                ongoing.collectLatest {
                    delay(2.minutes)
                    collected.emit(it)
                }
            }

            advanceUntilIdle()
            assertSame(Animal.Cat, result.await(), "Newest item is collected")
            job.cancelAndJoin()
        }
    }

    @Test
    fun unexpectedEnd()
    {
        runTest {
            val flow = flow {
                emit(Animal.Bird)
            }
            val ongoing = flow.asOngoing()

            val job = async {
                runCatching {
                    ongoing.collect {
                        delay(2.minutes)
                    }
                }
            }

            advanceUntilIdle()
            assertTrue(job.await().isFailure, "Exception thrown if flow ends")
            assertTrue(job.await().exceptionOrNull() is UnexpectedEndOfFlow, "Exception is UnexpectedEndOfFlow")
        }
    }

    @Test
    fun latestUnexpectedEnd()
    {
        runTest {
            val flow = flow {
                emit(Animal.Bird)
            }
            val ongoing = flow.asOngoing()

            val job = async {
                runCatching {
                    ongoing.collectLatest {
                        delay(2.minutes)
                    }
                }
            }

            advanceUntilIdle()
            assertTrue(job.await().isFailure, "Exception thrown if flow ends")
            assertTrue(job.await().exceptionOrNull() is UnexpectedEndOfFlow, "Exception is UnexpectedEndOfFlow")
        }
    }
}
