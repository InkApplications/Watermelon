package com.inkapplications.standard

/**
 * An exception that represents one or more exceptions.
 */
data class CompositeException(val exceptions: List<Throwable>): RuntimeException(
    "Multiple exceptions occurred: ${exceptions.joinToString()}"
) {
    init {
        if (exceptions.isEmpty()) throw IllegalArgumentException("exceptions is empty")
    }
}
