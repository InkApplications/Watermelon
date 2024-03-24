package com.inkapplications.data.transformer

import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultingTransformerTest {
    @Test
    fun testDefault() {
        val transformer = DefaultingTransformer(-1)
        assertEquals(-1, transformer.transform(null))
        assertEquals(-1, transformer.transform(-1))
        assertEquals(123, transformer.transform(123))
    }

    @Test
    fun testReverse() {
        val transformer = DefaultingTransformer(-1)
        assertEquals(-1, transformer.reverseTransform(-1))
        assertEquals(123, transformer.reverseTransform(123))
    }
}
