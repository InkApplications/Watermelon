package com.inkapplications.standard

/**
 * Map an Array's values, preserving the Array type.
 *
 * @receiver Starting array whose data will be mapped
 * @param mapping Transformation function to apply to each item in the array
 * @param T Starting data type
 * @param R Ending data type
 * @return A copy of the array with the data modified
 */
inline fun <T, reified R> Array<T>.mapArray(mapping: (T) -> R): Array<R> {
    return map(mapping).toTypedArray()
}

/**
 * Map a Set's values, preserving the Set type.
 *
 * @receiver Starting set whose data will be mapped
 * @param mapping Transformation function to apply to each item in the array
 * @param T Starting data type
 * @param R Ending data type
 * @return A copy of the Set with the data modified
 */
inline fun <T, R> Set<T>.mapSet(mapping: (T) -> R): Set<R> = map(mapping).toSet()

/**
 * Returns 6th *element* from the array.
 *
 * If the size of this array is less than 6, throws an [IndexOutOfBoundsException] except in Kotlin/JS
 * where the behavior is unspecified.
 */
inline operator fun <T> Array<out T>.component6(): T = get(5)

/**
 * Returns 7th *element* from the array.
 *
 * If the size of this array is less than 7, throws an [IndexOutOfBoundsException] except in Kotlin/JS
 * where the behavior is unspecified.
 */
inline operator fun <T> Array<out T>.component7(): T = get(6)

/**
 * Returns 8th *element* from the array.
 *
 * If the size of this array is less than 8, throws an [IndexOutOfBoundsException] except in Kotlin/JS
 * where the behavior is unspecified.
 */
inline operator fun <T> Array<out T>.component8(): T = get(7)

/**
 * Returns 9th *element* from the array.
 *
 * If the size of this array is less than 9, throws an [IndexOutOfBoundsException] except in Kotlin/JS
 * where the behavior is unspecified.
 */
inline operator fun <T> Array<out T>.component9(): T = get(8)

/**
 * Returns 10th *element* from the array.
 *
 * If the size of this array is less than 10, throws an [IndexOutOfBoundsException] except in Kotlin/JS
 * where the behavior is unspecified.
 */
inline operator fun <T> Array<out T>.component10(): T = get(9)
