package com.inkapplications.coroutines.ongoing

import com.inkapplications.coroutines.doubles.Animal
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertSame
import kotlin.test.assertTrue

class OngoingFlowTest
{
    @Test
    fun convertFlow()
    {
        val flow = channelFlow { send(Animal.Bird) }
        val ongoing = flow.asOngoing()

        assertSame(flow, ongoing.asFlow(), "Flow instance is preserved")
    }

    @Test
    fun ongoingFlowOf()
    {
        runTest {
            val ongoing = ongoingFlowOf(Animal.Bird, Animal.Cat)
            val collected = mutableListOf<Animal>()

            val job = launch { ongoing.collect { collected += it } }

            runCurrent()
            assertSame(Animal.Bird, collected[0], "First item is emitted")
            assertSame(Animal.Cat, collected[1], "Second item is emitted")
            assertTrue(job.isActive, "Flow does not close")
            job.cancelAndJoin()
        }
    }

    @Test
    fun ongoingFlowBuilder()
    {
        runTest {
            val ongoing = ongoingFlow { emit(Animal.Bird); emit(Animal.Cat) }
            val collected = mutableListOf<Animal>()

            val job = launch { ongoing.collect { collected += it } }

            runCurrent()
            assertSame(Animal.Bird, collected[0], "First item is emitted")
            assertSame(Animal.Cat, collected[1], "Second item is emitted")
            assertTrue(job.isActive, "Flow does not close")
            job.cancelAndJoin()
        }
    }
}
