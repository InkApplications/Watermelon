package com.inkapplications.coroutines

import com.inkapplications.coroutines.doubles.Animal
import com.inkapplications.coroutines.doubles.Plants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FlowsTest {
    @Test
    fun safeCollectTest() = runTest {
        var collected = 0
        flowOf(1, 2).safeCollect {
            ++collected
        }

        assertEquals(2, collected, "Safe collect run for each item in flow")

    }

//    @Test(expected = CancellationException::class)
//    fun safeCollectCancels() {
//        runBlocking {
//            (1..5).asFlow().safeCollect { value ->
//                if (value > 3) throw AssertionError("Flow should not be collected")
//                if (value == 3) cancel()
//            }
//        }
//    }

//    @Test
//    fun collectOnTest() = runTest {
//        val fixedThread = Thread()
//        val scope = Executors.newSingleThreadExecutor { fixedThread }.asCoroutineDispatcher().let(::CoroutineScope)
//
//        flowOf(1).collectOn(scope) {
//            assertEquals(fixedThread, Thread.currentThread(), "Run on correct scope")
//        }
//    }

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
            listOf(Animal.Dog, Plants.Flower),
            listOf(Animal.Cat, Plants.Tree, Animal.Bird)
        )

        val result = initial.filterItemIsInstance<Animal>().toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        val first = result[0]
        assertEquals(1, first.size, "Filter only emits items of the correct type")
        assertTrue(first[0] is Animal.Dog, "Filter only emits items of the correct type")

        val second = result[1]
        assertEquals(2, second.size, "Filter only emits items of the correct type")
        assertTrue(second.all { it is Animal }, "Filter only emits items of the correct type")
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

    @Test
    fun mapItemsCatchingTest() = runTest {
        val initial = flowOf(listOf(1, null))

        val result = initial.mapItemsCatching { it!! }.toList()

        assertEquals(2, result.first().size, "All items are mapped to a result")
        assertTrue(result.first()[0].isSuccess, "Successful maps have successful result class")
        assertEquals(1, result.first()[0].getOrNull())
        assertTrue(result.first()[1].isFailure, "Thrown exceptions are caught as result failure")
        assertTrue(result.first()[1].exceptionOrNull() is NullPointerException, "Expected exception is preserved")
    }

    @Test
    fun filterItemSuccessTest() = runTest {
        val initial = flowOf(listOf(
            Result.success(1),
            Result.failure(Throwable())
        ))

        val result = initial.filterItemSuccess().toList()

        assertEquals(1, result.first()[0])
    }

    @Test
    fun filterItemFailureTest() = runTest {
        val error = Throwable()
        val initial = flowOf(listOf(
            Result.success(1),
            Result.failure(error)
        ))

        val result = initial.filterItemFailure().toList()

        assertEquals(error, result.first()[0])
    }

    @Test
    fun combineApplyTest() = runTest {
        class Target {
            var a: String? = null
            var b: String? = null
        }

        val first = flowOf("test")
        val second = flowOf("test2")

        val result = flowOf(Target())
            .combineApply(first) {
                a = it
            }
            .combineApply(second) {
                b = it
            }
            .toList()

        assertEquals("test", result.first().a)
        assertEquals("test2", result.first().b)
    }

    @Test
    fun combineTest() = runTest {
        val flow1 = flowOf(1)
        val flow2 = flowOf(2)
        val flow3 = flowOf(3)
        val flow4 = flowOf(4)
        val flow5 = flowOf(5)
        val flow6 = flowOf(6)
        val flow7 = flowOf(7)
        val flow8 = flowOf(8)
        val flow9 = flowOf(9)
        val flow10 = flowOf(10)

        val result6 = combine(
            flow1,
            flow2,
            flow3,
            flow4,
            flow5,
            flow6,
        ) { a, b, c, d, e, f ->
            listOf(a, b, c, d, e, f)
        }.toList()
        val result7 = combine(
            flow1,
            flow2,
            flow3,
            flow4,
            flow5,
            flow6,
            flow7,
        ) { a, b, c, d, e, f, g ->
            listOf(a, b, c, d, e, f, g)
        }.toList()
        val result8 = combine(
            flow1,
            flow2,
            flow3,
            flow4,
            flow5,
            flow6,
            flow7,
            flow8,
        ) { a, b, c, d, e, f, g, h ->
            listOf(a, b, c, d, e, f, g, h)
        }.toList()
        val result9 = combine(
            flow1,
            flow2,
            flow3,
            flow4,
            flow5,
            flow6,
            flow7,
            flow8,
            flow9,
        ) { a, b, c, d, e, f, g, h, i ->
            listOf(a, b, c, d, e, f, g, h, i)
        }.toList()
        val result10 = combine(
            flow1,
            flow2,
            flow3,
            flow4,
            flow5,
            flow6,
            flow7,
            flow8,
            flow9,
            flow10,
        ) { a, b, c, d, e, f, g, h, i, j ->
            listOf(a, b, c, d, e, f, g, h, i, j)
        }.toList()

        assertEquals(listOf(1, 2, 3, 4, 5, 6), result6.first())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7), result7.first())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8), result8.first())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9), result9.first())
        assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), result10.first())
    }
}
