package com.inkapplications.data.transformer

object StringTransformations {
    val StringToInt: DataTransformer<String, Int> = SimpleTransformer(
        transformer = String::toInt,
        reverseTransformer = Int::toString,
    )

    val StringToLong: DataTransformer<String, Long> = SimpleTransformer(
        transformer = String::toLong,
        reverseTransformer = Long::toString,
    )

    val StringToFloat: DataTransformer<String, Float> = SimpleTransformer(
        transformer = String::toFloat,
        reverseTransformer = { it.toString().let { if ('.' !in it) { "$it.0" } else it } }
    )

    val StringToDouble: DataTransformer<String, Double> = SimpleTransformer(
        transformer = String::toDouble,
        reverseTransformer = { it.toString().let { if ('.' !in it) { "$it.0" } else it } },
    )

    val StringToBoolean: DataTransformer<String, Boolean> = SimpleTransformer(
        transformer = String::toBoolean,
        reverseTransformer = Boolean::toString,
    )

    val Trimming: DataTransformer<String, String> = SimpleTransformer(
        transformer = String::trim,
        reverseTransformer = String::trim,
    )

    val EmptyStringToNull: DataTransformer<String, String?> = SimpleTransformer(
        transformer = { it.ifEmpty { null } },
        reverseTransformer = { it ?: "" },
    )

    val None: DataTransformer<String, String> = NoTransformation()
}
