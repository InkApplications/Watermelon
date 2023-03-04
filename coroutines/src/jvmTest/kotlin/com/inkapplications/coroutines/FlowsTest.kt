package com.inkapplications.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import java.util.concurrent.Executors
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FlowsTest {
    @Test
    fun safeCollectTest() = runTest {
        var collected = 0
        flowOf(1, 2).safeCollect {
            ++collected
        }

        assertEquals(2, collected, "Safe collect run for each item in flow")

    }

    @Test(expected = CancellationException::class)
    fun safeCollectCancels() {
        runBlocking {
            (1..5).asFlow().safeCollect { value ->
                if (value > 3) throw AssertionError("Flow should not be collected")
                if (value == 3) cancel()
            }
        }
    }

    @Test
    fun collectOnTest() = runTest {
        val fixedThread = Thread()
        val scope = Executors.newSingleThreadExecutor { fixedThread }.asCoroutineDispatcher().let(::CoroutineScope)

        flowOf(1).collectOn(scope) {
            assertEquals(fixedThread, Thread.currentThread(), "Run on correct scope")
        }
    }

    @Test
    fun mapEachTest() = runTest {
        val initial = flowOf(
            listOf(1, 2),
            listOf(3, 4)
        )

        val result = initial.mapItems { it * 10 }.toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(listOf(10, 20), result[0], "Mapping is applied to each result")
        assertEquals(listOf(30, 40), result[1], "Mapping is applied to each result")
    }

    @Test
    fun filterEachTest() = runTest {
        val initial = flowOf(
            listOf(1, 2),
            listOf(3, 4)
        )

        val result = initial.filterItems { it % 2 == 0 }.toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(listOf(2), result[0], "Filter is applied to each result")
        assertEquals(listOf(4), result[1], "Filter is applied to each result")
    }

    @Test
    fun filterEachIsInstanceTest() = runTest {
        val initial = flowOf(
            listOf(1.0, 2f),
            listOf(3f, 4.0)
        )

        val result = initial.filterItemIsInstance<Float>().toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(listOf(2f), result[0], "Filter is applied to each result")
        assertEquals(listOf(3f), result[1], "Filter is applied to each result")
    }

    @Test
    fun filterEachNotNullTest() = runTest {
        val initial = flowOf(
            listOf(null),
            listOf(null, 4)
        )

        val result = initial.filterItemNotNull().toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(emptyList(), result[0], "Filter keeps empty lists")
        assertEquals(listOf(4), result[1], "Filter removes null items from list")
    }

    @Test
    fun combinePairTest() = runTest {
        val first = flowOf(1)
        val second = flowOf(2)

        val result = first.combinePair(second).toList()

        assertEquals(listOf(1 to 2), result, "Flows latest results are combined as a pair")
    }

    @Test
    fun combineTripleTest() = runTest {
        val first = flowOf(1)
        val second = flowOf(2)
        val third = flowOf(3)

        val result = first.combinePair(second).combineTriple(third).toList()

        assertEquals(listOf(Triple(1, 2, 3)), result, "Flows latest results are combined as a triple")
    }

    @Test
    fun combineFlattenTest() = runTest {
        val first = flowOf(listOf(1, 2))
        val second = flowOf(listOf(3, 4))

        val result = first.combineFlatten(second).toList()

        assertEquals(1, result.size, "Flows are combined")
        assertEquals(listOf(1, 2, 3, 4), result[0], "Flows are combined into a flattened result")
    }
}
