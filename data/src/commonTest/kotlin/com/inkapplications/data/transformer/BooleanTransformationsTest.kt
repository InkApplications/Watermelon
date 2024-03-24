package com.inkapplications.data.transformer

import com.inkapplications.data.transformer.BooleanTransformations.BooleanToDouble
import com.inkapplications.data.transformer.BooleanTransformations.BooleanToFloat
import com.inkapplications.data.transformer.BooleanTransformations.BooleanToInt
import com.inkapplications.data.transformer.BooleanTransformations.BooleanToLong
import com.inkapplications.data.transformer.BooleanTransformations.BooleanToString
import kotlin.test.Test
import kotlin.test.assertEquals

class BooleanTransformationsTest {
    @Test
    fun stringTransformations() {
        assertEquals("true", BooleanToString.transform(true))
        assertEquals("false", BooleanToString.transform(false))
        assertEquals(true, BooleanToString.reverseTransform("true"))
        assertEquals(false, BooleanToString.reverseTransform("false"))

    }

    @Test
    fun numberTransformations() {
        assertEquals(1, BooleanToInt.transform(true))
        assertEquals(0, BooleanToInt.transform(false))
        assertEquals(true, BooleanToInt.reverseTransform(1))
        assertEquals(false, BooleanToInt.reverseTransform(0))
        assertEquals(true, BooleanToInt.reverseTransform(2), "Any non-zero value should be true")

        assertEquals(1L, BooleanToLong.transform(true))
        assertEquals(0L, BooleanToLong.transform(false))
        assertEquals(true, BooleanToLong.reverseTransform(1L))
        assertEquals(false, BooleanToLong.reverseTransform(0L))
        assertEquals(true, BooleanToLong.reverseTransform(2L), "Any non-zero value should be true")

        assertEquals(1f, BooleanToFloat.transform(true))
        assertEquals(0f, BooleanToFloat.transform(false))
        assertEquals(true, BooleanToFloat.reverseTransform(1f))
        assertEquals(false, BooleanToFloat.reverseTransform(0f))
        assertEquals(true, BooleanToFloat.reverseTransform(2f), "Any non-zero value should be true")

        assertEquals(1.0, BooleanToDouble.transform(true))
        assertEquals(0.0, BooleanToDouble.transform(false))
        assertEquals(true, BooleanToDouble.reverseTransform(1.0))
        assertEquals(false, BooleanToDouble.reverseTransform(0.0))
        assertEquals(true, BooleanToDouble.reverseTransform(2.0), "Any non-zero value should be true")
    }

    @Test
    fun noTransformation() {
        assertEquals(true, BooleanTransformations.None.transform(true))
        assertEquals(false, BooleanTransformations.None.transform(false))
        assertEquals(true, BooleanTransformations.None.reverseTransform(true))
        assertEquals(false, BooleanTransformations.None.reverseTransform(false))
    }
}
