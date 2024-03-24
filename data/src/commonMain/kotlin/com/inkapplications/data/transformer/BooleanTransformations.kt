package com.inkapplications.data.transformer

object BooleanTransformations {
    val BooleanToString: DataTransformer<Boolean, String> = StringTransformations.StringToBoolean.reverse()
    val BooleanToInt: DataTransformer<Boolean, Int> = IntTransformations.IntToBoolean.reverse()
    val BooleanToLong: DataTransformer<Boolean, Long> = LongTransformations.LongToBoolean.reverse()
    val BooleanToFloat: DataTransformer<Boolean, Float> = FloatTransformations.FloatToBoolean.reverse()
    val BooleanToDouble: DataTransformer<Boolean, Double> = DoubleTransformations.DoubleToBoolean.reverse()
    val None: DataTransformer<Boolean, Boolean> = NoTransformation()
}
