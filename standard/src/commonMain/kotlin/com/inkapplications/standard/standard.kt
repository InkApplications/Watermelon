/**
 * Standard "junk drawer" to put things in until we can figure out better organization.
 */
package com.inkapplications.standard

/**
 * Tries to run the function [block] [times] number of times catching [times]-1 exceptions and trying again.
 * On the last execution if there is an exception it will be bubbled up to the caller.
 *
 * @param times default 3, the number of times the block will be tried.
 * @param block code block that will be run.
 */
inline fun <T> retry(times: Int = 3, block: () -> T): T {
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) { }
    }

    return block()
}
