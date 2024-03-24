package com.inkapplications.data.transformer

import kotlin.test.Test
import kotlin.test.assertEquals

class SentinelAsNullTransformerTest {
    @Test
    fun testSentinel() {
        val transformer = SentinelAsNullTransformer(-1)
        assertEquals(null, transformer.transform(-1))
        assertEquals(123, transformer.transform(123))
    }

    @Test
    fun testReverse() {
        val transformer = SentinelAsNullTransformer(-1)
        assertEquals(-1, transformer.reverseTransform(null))
        assertEquals(-1, transformer.reverseTransform(-1))
        assertEquals(123, transformer.reverseTransform(123))
    }
}
