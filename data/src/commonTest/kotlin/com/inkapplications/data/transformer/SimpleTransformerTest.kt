package com.inkapplications.data.transformer

import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleTransformerTest {
    @Test
    fun testTransform() {
        val transformer = SimpleTransformer<Int, String>(
            transformer = { "foo" },
            reverseTransformer = { 123 }
        )
        assertEquals("foo", transformer.transform(0))
        assertEquals(123, transformer.reverseTransform("test"))
    }
}
