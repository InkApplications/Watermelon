package com.inkapplications.data.transformer

import com.inkapplications.data.transformer.IntTransformations.IntToBoolean
import com.inkapplications.data.transformer.IntTransformations.IntToDouble
import com.inkapplications.data.transformer.IntTransformations.IntToFloat
import com.inkapplications.data.transformer.IntTransformations.IntToLong
import com.inkapplications.data.transformer.IntTransformations.IntToString
import com.inkapplications.data.transformer.IntTransformations.None
import kotlin.test.Test
import kotlin.test.assertEquals

class IntTransformationsTest {
    @Test
    fun stringTransformations() {
        assertEquals("1", IntToString.transform(1))
        assertEquals("0", IntToString.transform(0))
        assertEquals(1, IntToString.reverseTransform("1"))
        assertEquals(0, IntToString.reverseTransform("0"))
    }

    @Test
    fun booleanTransformations() {
        assertEquals(false, IntToBoolean.transform(0))
        assertEquals(true, IntToBoolean.transform(1))
        assertEquals(true, IntToBoolean.transform(2), "Any non-zero value should be true")
        assertEquals(0, IntToBoolean.reverseTransform(false))
        assertEquals(1, IntToBoolean.reverseTransform(true))
    }

    @Test
    fun numberTransformations() {
        assertEquals(12, IntToLong.transform(12))
        assertEquals(12, IntToLong.transform(12))
        assertEquals(12, IntToLong.reverseTransform(12))

        assertEquals(12.0f, IntToFloat.transform(12), 1e-6f)
        assertEquals(12, IntToFloat.reverseTransform(12.3f))
        assertEquals(12, IntToFloat.reverseTransform(12.9f))

        assertEquals(12.0, IntToDouble.transform(12), 1e-16)
        assertEquals(12, IntToDouble.reverseTransform(12.3))
        assertEquals(12, IntToDouble.reverseTransform(12.9))
    }

    @Test
    fun noTransformation() {
        assertEquals(12, None.transform(12))
        assertEquals(12, None.reverseTransform(12))
    }
}
