package com.inkapplications.data.transformer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class DataTransformerTest {
    private val stub = object: DataTransformer<Any, Any> {
        override fun transform(data: Any): Any = B
        override fun reverseTransform(data: Any): Any = A
    }

    @Test
    fun reverseTest() {
        val transformer = stub.reverse()

        assertSame(A, transformer.transform(0))
        assertSame(B, transformer.reverseTransform(0))
    }

    @Test
    fun withReverseTest() {
        val transformer = stub::transform.withReverse(stub::reverseTransform)

        assertSame(B, transformer.transform(0))
        assertSame(A, transformer.reverseTransform(0))
    }

    @Test
    fun nullable() {
        val transformer = stub.nullable()

        assertEquals(null, transformer.transform(null))
        assertEquals(null, transformer.reverseTransform(null))
    }

    @Test
    fun then() {
        val transformer = stub.then(object: DataTransformer<Any, Any> {
            override fun transform(data: Any): Any = C
            override fun reverseTransform(data: Any): Any = B
        })

        assertSame(C, transformer.transform(0))
        assertSame(A, transformer.reverseTransform(0))
    }

    data object A
    data object B
    data object C
}
