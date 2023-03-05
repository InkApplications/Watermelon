package com.inkapplications.standard

import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionsTest {
    @Test
    fun mapSet() {
        val initial = setOf("a", "b")
        val result = initial.mapSet { it.uppercase() }

        assertEquals(setOf("A", "B"), result)
    }

    @Test
    fun mapArray() {
        val initial = arrayOf("a", "b")
        val result = initial.mapArray { it.uppercase() }

        assertEquals("A", result[0])
        assertEquals("B", result[1])
    }
}
