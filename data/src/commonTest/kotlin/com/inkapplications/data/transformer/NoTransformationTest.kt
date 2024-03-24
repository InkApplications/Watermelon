package com.inkapplications.data.transformer

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertSame

class NoTransformationTest {
    @Test
    fun testNoTransformation() {
        val data = TestObject()
        val transformer = NoTransformation<TestObject>()

        assertSame(data, transformer.transform(data))
        assertSame(data, transformer.reverseTransform(data))
    }

    private data class TestObject(val data: Int = Random.nextInt())
}
