package com.inkapplications.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import java.util.concurrent.Executors
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmFlowsTest
{
    @Test(expected = CancellationException::class)
    fun safeCollectCancels()
    {
        runBlocking {
            (1..5).asFlow().safeCollect { value ->
                if (value > 3) throw AssertionError("Flow should not be collected")
                if (value == 3) cancel()
            }
        }
    }

    @Test
    fun collectOnTest()
    {
        runTest {
            val fixedThread = Thread()
            val scope = Executors.newSingleThreadExecutor { fixedThread }.asCoroutineDispatcher().let(::CoroutineScope)

            flowOf(1).collectOn(scope) {
                assertEquals(fixedThread, Thread.currentThread(), "Run on correct scope")
            }
        }
    }
}
