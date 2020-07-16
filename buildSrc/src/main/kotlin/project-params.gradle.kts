/**
 * Attempts to load values needed through out the project. All configuration
 * parameters live here.
 *
 * Usage:
 * ```
 * plugins {
 *   id("project-params")
 * }
 * ```
 */

/*
inkapplications.mindfreak.bintrayUsername or INKAPPLICATIONS_MINDFREAK_BINTRAY_USERNAME
should be set in order to publish artifacts to bintray.
Usage `val bintrayUsername by project`
 */
loadExtra("bintrayUsername")

/*
inkapplications.mindfreak.bintrayPassword or INKAPPLICATIONS_MINDFREAK_BINTRAY_PASSWORD
should be set in order to publish artifacts to bintray
Usage `val bintrayPassword by project`
 */
loadExtra("bintrayPassword")
