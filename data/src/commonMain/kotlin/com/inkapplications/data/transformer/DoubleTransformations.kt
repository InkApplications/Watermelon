package com.inkapplications.data.transformer

import kotlin.math.roundToInt
import kotlin.math.roundToLong

object DoubleTransformations {
    val DoubleToString: DataTransformer<Double, String> = StringTransformations.StringToDouble.reverse()
    val DoubleToInt: DataTransformer<Double, Int> = SimpleTransformer(
        transformer = Double::toInt,
        reverseTransformer = Int::toDouble
    )
    val DoubleToRoundedInt: DataTransformer<Double, Int> = SimpleTransformer(
        transformer = Double::roundToInt,
        reverseTransformer = Int::toDouble
    )
    val DoubleToLong: DataTransformer<Double, Long> = SimpleTransformer(
        transformer = Double::toLong,
        reverseTransformer = Long::toDouble
    )
    val DoubleToRoundedLong: DataTransformer<Double, Long> = SimpleTransformer(
        transformer = Double::roundToLong,
        reverseTransformer = Long::toDouble
    )
    val DoubleToFloat: DataTransformer<Double, Float> = SimpleTransformer(
        transformer = Double::toFloat,
        reverseTransformer = Float::toDouble
    )
    val DoubleToBoolean: DataTransformer<Double, Boolean> = SimpleTransformer(
        transformer = { it != 0.0 },
        reverseTransformer = { if (it) 1.0 else 0.0 }
    )
    val None: DataTransformer<Double, Double> = NoTransformation()
}
