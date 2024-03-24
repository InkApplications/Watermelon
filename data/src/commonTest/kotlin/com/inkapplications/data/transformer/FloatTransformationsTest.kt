package com.inkapplications.data.transformer

import com.inkapplications.data.transformer.FloatTransformations.FloatToBoolean
import com.inkapplications.data.transformer.FloatTransformations.FloatToDouble
import com.inkapplications.data.transformer.FloatTransformations.FloatToInt
import com.inkapplications.data.transformer.FloatTransformations.FloatToLong
import com.inkapplications.data.transformer.FloatTransformations.FloatToRoundedInt
import com.inkapplications.data.transformer.FloatTransformations.FloatToRoundedLong
import com.inkapplications.data.transformer.FloatTransformations.FloatToString
import com.inkapplications.data.transformer.FloatTransformations.None
import kotlin.test.Test
import kotlin.test.assertEquals

class FloatTransformationsTest {
    @Test
    fun stringTransformations() {
        assertEquals("1.0", FloatToString.transform(1.0f))
        assertEquals("0.0", FloatToString.transform(0.0f))
        assertEquals(1.0f, FloatToString.reverseTransform("1.0"))
        assertEquals(0.0f, FloatToString.reverseTransform("0.0"))
    }

    @Test
    fun booleanTransformations() {
        assertEquals(false, FloatToBoolean.transform(0.0f))
        assertEquals(true, FloatToBoolean.transform(1.0f))
        assertEquals(true, FloatToBoolean.transform(.1f), "Any non-zero value should be true")
        assertEquals(0.0f, FloatToBoolean.reverseTransform(false))
        assertEquals(1.0f, FloatToBoolean.reverseTransform(true))
    }

    @Test
    fun numberTransformations() {
        assertEquals(12, FloatToInt.transform(12.3f))
        assertEquals(12, FloatToInt.transform(12.9f))
        assertEquals(12.0f, FloatToInt.reverseTransform(12), 1e-6f)

        assertEquals(12, FloatToRoundedInt.transform(12.3f))
        assertEquals(13, FloatToRoundedInt.transform(12.6f))

        assertEquals(12L, FloatToLong.transform(12.3f))
        assertEquals(12L, FloatToLong.transform(12.9f))
        assertEquals(12.0f, FloatToLong.reverseTransform(12L), 1e-6f)

        assertEquals(12L, FloatToRoundedLong.transform(12.3f))
        assertEquals(13L, FloatToRoundedLong.transform(12.6f))

        assertEquals(12.3, FloatToDouble.transform(12.3f), 1e-6)
        assertEquals(12.9, FloatToDouble.transform(12.9f), 1e-6)
        assertEquals(12.0f, FloatToDouble.reverseTransform(12.0), 1e-6f)
    }

    @Test
    fun noTransformation() {
        assertEquals(12.3f, None.transform(12.3f))
        assertEquals(12.3f, None.reverseTransform(12.3f))
    }
}
