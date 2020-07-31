/**
 * Extensions on any object.
 */
package com.inkapplications.standard

/**
 * Add after a when statement to force it to be exhaustive with out having to set
 * the result to a variable.
 *
 * ex.
 * ```
 * when(result) {
 *   is Success -> println("yay")
 *   is Failure -> println(":(")
 * }.exhaustive
 * ```
 *
 * Now if any new subtypes are added to the result class exhaustive would cause you to get a compiler failure
 * and you can handle the new types.
 */
val <T> T.exhaustive: T get() = this
