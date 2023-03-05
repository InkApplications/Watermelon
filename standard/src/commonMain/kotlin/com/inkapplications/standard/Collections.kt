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
