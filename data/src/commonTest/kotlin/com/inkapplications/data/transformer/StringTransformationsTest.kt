package com.inkapplications.data.transformer

import com.inkapplications.data.transformer.StringTransformations.EmptyStringToNull
import com.inkapplications.data.transformer.StringTransformations.None
import com.inkapplications.data.transformer.StringTransformations.StringToBoolean
import com.inkapplications.data.transformer.StringTransformations.StringToDouble
import com.inkapplications.data.transformer.StringTransformations.StringToFloat
import com.inkapplications.data.transformer.StringTransformations.StringToInt
import com.inkapplications.data.transformer.StringTransformations.StringToLong
import com.inkapplications.data.transformer.StringTransformations.Trimming
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class StringTransformationsTest {
    @Test
    fun booleanTransformations() {
        assertEquals(true, StringToBoolean.transform("true"))
        assertEquals(false, StringToBoolean.transform("false"))
        assertEquals(false, StringToBoolean.transform("foo"), "Transformation is not strict")
    }

    @Test
    fun trimming() {
        assertEquals("foo", Trimming.transform(" foo "))
        assertEquals("foo", Trimming.reverseTransform(" foo "))
    }

    @Test
    fun emptyToNull() {
        assertEquals("foo", EmptyStringToNull.transform("foo"))
        assertEquals("foo", EmptyStringToNull.reverseTransform("foo"))
        assertEquals(null, EmptyStringToNull.transform(""))
        assertEquals("", EmptyStringToNull.reverseTransform(null))
        assertEquals("", EmptyStringToNull.reverseTransform(""))
    }

    @Test
    fun numberTransformations() {
        assertEquals(123, StringToInt.transform("123"))
        assertEquals("123", StringToInt.reverseTransform(123))

        assertEquals(123L, StringToLong.transform("123"))
        assertEquals("123", StringToLong.reverseTransform(123L))

        assertEquals(123f, StringToFloat.transform("123"))
        assertEquals("123.0", StringToFloat.reverseTransform(123f))

        assertEquals(123.0, StringToDouble.transform("123"))
        assertEquals("123.0", StringToDouble.reverseTransform(123.0))
    }

    @Test
    fun noTransformation() {
        assertEquals("foo", None.transform("foo"))
        assertEquals("foo", None.reverseTransform("foo"))
    }
}
