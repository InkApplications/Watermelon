package com.inkapplications.coroutines

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FlowsTest {
    @Test
    fun mapEachTest() = runBlockingTest {
        val initial = flowOf(
            listOf(1, 2),
            listOf(3, 4)
        )

        val result = initial.mapEach { it * 10 }.toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(listOf(10, 20), result[0], "Mapping is applied to each result")
        assertEquals(listOf(30, 40), result[1], "Mapping is applied to each result")
    }

    @Test
    fun filterEachTest() = runBlockingTest {
        val initial = flowOf(
            listOf(1, 2),
            listOf(3, 4)
        )

        val result = initial.filterEach { it % 2 == 0 }.toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(listOf(2), result[0], "Filter is applied to each result")
        assertEquals(listOf(4), result[1], "Filter is applied to each result")
    }

    @Test
    fun filterEachNotNullTest() = runBlockingTest {
        val initial = flowOf(
            listOf(null),
            listOf(null, 4)
        )

        val result = initial.filterEachNotNull().toList()

        assertEquals(2, result.size, "Each item has a 1:1 mapping")
        assertEquals(emptyList(), result[0], "Filter keeps empty lists")
        assertEquals(listOf(4), result[1], "Filter removes null items from list")
    }

    @Test
    fun combinePairTest() = runBlockingTest {
        val first = flowOf(1)
        val second = flowOf(2)

        val result = first.combinePair(second).toList()

        assertEquals(listOf(1 to 2), result, "Flows latest results are combined as a pair")
    }

    @Test
    fun combineTripleTest() = runBlockingTest {
        val first = flowOf(1)
        val second = flowOf(2)
        val third = flowOf(3)

        val result = first.combinePair(second).combineTriple(third).toList()

        assertEquals(listOf(Triple(1, 2, 3)), result, "Flows latest results are combined as a triple")
    }
}
