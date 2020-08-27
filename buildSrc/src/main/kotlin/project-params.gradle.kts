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
inkapplications.watermelon.bintrayUsername or INKAPPLICATIONS_WATERMELON_BINTRAY_USERNAME
should be set in order to publish artifacts to bintray.
Usage `val bintrayUsername by project`
 */
loadExtra("bintrayUsername")

/*
inkapplications.watermelon.bintrayPassword or INKAPPLICATIONS_WATERMELON_BINTRAY_PASSWORD
should be set in order to publish artifacts to bintray
Usage `val bintrayPassword by project`
 */
loadExtra("bintrayPassword")
