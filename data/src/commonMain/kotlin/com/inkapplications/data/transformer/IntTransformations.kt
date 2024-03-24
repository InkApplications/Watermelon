package com.inkapplications.data.transformer

object IntTransformations {
    val IntToString: DataTransformer<Int, String> = StringTransformations.StringToInt.reverse()
    val IntToBoolean: DataTransformer<Int, Boolean> = SimpleTransformer(
        transformer = { it != 0 },
        reverseTransformer = { if (it) 1 else 0 }
    )
    val IntToLong: DataTransformer<Int, Long> = LongTransformations.LongToInt.reverse()
    val IntToFloat: DataTransformer<Int, Float> = FloatTransformations.FloatToInt.reverse()
    val IntToDouble: DataTransformer<Int, Double> = DoubleTransformations.DoubleToInt.reverse()
    val None: DataTransformer<Int, Int> = NoTransformation()
}
