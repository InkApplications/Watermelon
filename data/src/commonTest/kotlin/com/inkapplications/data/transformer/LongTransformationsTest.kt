package com.inkapplications.data.transformer

import com.inkapplications.data.transformer.LongTransformations.LongToBoolean
import com.inkapplications.data.transformer.LongTransformations.LongToDouble
import com.inkapplications.data.transformer.LongTransformations.LongToFloat
import com.inkapplications.data.transformer.LongTransformations.LongToInt
import com.inkapplications.data.transformer.LongTransformations.LongToString
import kotlin.test.Test
import kotlin.test.assertEquals

class LongTransformationsTest {
    @Test
    fun stringTransformations() {
        assertEquals("1", LongToString.transform(1))
        assertEquals("0", LongToString.transform(0))
        assertEquals(1L, LongToString.reverseTransform("1"))
        assertEquals(0L, LongToString.reverseTransform("0"))
    }

    @Test
    fun booleanTransformations() {
        assertEquals(false, LongToBoolean.transform(0L))
        assertEquals(true, LongToBoolean.transform(1L))
        assertEquals(true, LongToBoolean.transform(2L), "Any non-zero value should be true")
        assertEquals(0L, LongToBoolean.reverseTransform(false))
        assertEquals(1L, LongToBoolean.reverseTransform(true))
    }

    @Test
    fun numberTransformations() {
        assertEquals(12, LongToInt.transform(12L))
        assertEquals(12, LongToInt.transform(12L))
        assertEquals(12L, LongToInt.reverseTransform(12))

        assertEquals(12.0f, LongToFloat.transform(12L))
        assertEquals(12.0f, LongToFloat.transform(12L))
        assertEquals(12L, LongToFloat.reverseTransform(12f))

        assertEquals(12.0, LongToDouble.transform(12L))
        assertEquals(12.0, LongToDouble.transform(12L))
        assertEquals(12L, LongToDouble.reverseTransform(12.0))
    }

    @Test
    fun noTransformation() {
        assertEquals(12L, LongTransformations.None.transform(12L))
        assertEquals(12L, LongTransformations.None.reverseTransform(12L))
    }
}

