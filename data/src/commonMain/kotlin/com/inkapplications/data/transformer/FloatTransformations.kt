package com.inkapplications.data.transformer

import kotlin.math.roundToInt
import kotlin.math.roundToLong

object FloatTransformations {
    val FloatToString: DataTransformer<Float, String> = StringTransformations.StringToFloat.reverse()
    val FloatToBoolean: DataTransformer<Float, Boolean> = SimpleTransformer(
        transformer = { it != 0f },
        reverseTransformer = { if (it) 1f else 0f },
    )
    val FloatToInt: DataTransformer<Float, Int> = SimpleTransformer(
        transformer = Float::toInt,
        reverseTransformer = Int::toFloat,
    )
    val FloatToRoundedInt: DataTransformer<Float, Int> = SimpleTransformer(
        transformer = Float::roundToInt,
        reverseTransformer = Int::toFloat,
    )
    val FloatToLong: DataTransformer<Float, Long> = SimpleTransformer(
        transformer = Float::toLong,
        reverseTransformer = Long::toFloat,
    )
    val FloatToRoundedLong: DataTransformer<Float, Long> = SimpleTransformer(
        transformer = Float::roundToLong,
        reverseTransformer = Long::toFloat,
    )
    val FloatToDouble: DataTransformer<Float, Double> = DoubleTransformations.DoubleToFloat.reverse()
    val None: DataTransformer<Float, Float> = NoTransformation()
}
