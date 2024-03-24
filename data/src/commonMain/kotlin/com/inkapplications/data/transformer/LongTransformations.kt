package com.inkapplications.data.transformer

object LongTransformations {
    val LongToString: DataTransformer<Long, String> = StringTransformations.StringToLong.reverse()
    val LongToBoolean: DataTransformer<Long, Boolean> = SimpleTransformer(
        transformer = { it != 0L },
        reverseTransformer = { if (it) 1L else 0L }
    )
    val LongToInt: DataTransformer<Long, Int> = SimpleTransformer(
        transformer = Long::toInt,
        reverseTransformer = Int::toLong
    )
    val LongToFloat: DataTransformer<Long, Float> = FloatTransformations.FloatToLong.reverse()
    val LongToDouble: DataTransformer<Long, Double> = DoubleTransformations.DoubleToLong.reverse()
    val None: DataTransformer<Long, Long> = NoTransformation()
}
