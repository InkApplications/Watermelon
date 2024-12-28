package com.inkapplications.coroutines.ongoing

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TransformationsTest
{
    @Test
    fun unsafeTransform()
    {
        runTest {
            val original = ongoingFlowOf(1, 2, 3)
            val transformed = original.unsafeTransform { map { it * 2 } }
            val collected = mutableListOf<Int>()

            val result = launch { transformed.collect { collected += it } }

            runCurrent()
            assertEquals(listOf(2, 4, 6), collected, "Transformation is applied to each item")
            result.cancelAndJoin()
        }
    }
}
