package com.inkapplications.data.transformer

import com.inkapplications.data.transformer.DoubleTransformations.DoubleToBoolean
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToFloat
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToInt
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToLong
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToRoundedInt
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToRoundedLong
import com.inkapplications.data.transformer.DoubleTransformations.DoubleToString
import com.inkapplications.data.transformer.DoubleTransformations.None
import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleTransformationsTest {
    @Test
    fun stringTransformations() {
        assertEquals("1.0", DoubleToString.transform(1.0))
        assertEquals("0.0", DoubleToString.transform(0.0))
        assertEquals(1.0, DoubleToString.reverseTransform("1.0"))
        assertEquals(0.0, DoubleToString.reverseTransform("0.0"))
    }

    @Test
    fun booleanTransformations() {
        assertEquals(false, DoubleToBoolean.transform(0.0))
        assertEquals(true, DoubleToBoolean.transform(1.0))
        assertEquals(true, DoubleToBoolean.transform(.1), "Any non-zero value should be true")
        assertEquals(0.0, DoubleToBoolean.reverseTransform(false))
        assertEquals(1.0, DoubleToBoolean.reverseTransform(true))
    }

    @Test
    fun numberTransformations() {
        assertEquals(12, DoubleToInt.transform(12.3))
        assertEquals(12, DoubleToInt.transform(12.9))
        assertEquals(12.0, DoubleToInt.reverseTransform(12), 1e-16)

        assertEquals(12, DoubleToRoundedInt.transform(12.3))
        assertEquals(13, DoubleToRoundedInt.transform(12.6))

        assertEquals(12L, DoubleToLong.transform(12.3))
        assertEquals(12L, DoubleToLong.transform(12.9))
        assertEquals(12.0, DoubleToLong.reverseTransform(12L), 1e-16)

        assertEquals(12L, DoubleToRoundedLong.transform(12.3))
        assertEquals(13L, DoubleToRoundedLong.transform(12.6))

        assertEquals(12.3f, DoubleToFloat.transform(12.3))
        assertEquals(12.9f, DoubleToFloat.transform(12.9))
        assertEquals(12.0, DoubleToFloat.reverseTransform(12f), 1e-16)
    }

    @Test
    fun noTransformation() {
        assertEquals(12.3, None.transform(12.3))
        assertEquals(12.3, None.reverseTransform(12.3))
    }
}
