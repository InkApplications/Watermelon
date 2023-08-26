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

    @Test
    fun components() {
        val test = (1..10).toList().toTypedArray()

        assertEquals(6, test.component6())
        assertEquals(7, test.component7())
        assertEquals(8, test.component8())
        assertEquals(9, test.component9())
        assertEquals(10, test.component10())
    }
}
