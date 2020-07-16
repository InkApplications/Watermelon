import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

/**
 * Loads a value from project properties or from environemnt variables.
 * namespace for project properties is `cash.andrew.mntrail` and `MN_TRAIL_INFO`
 * for environment variables.
 */
fun Project.loadExtra(key: String, defaultValue: String = "") {
    val propertyKey = "inkapplications.mindfreak.$key"
    val extraKey = key.split(".")
        .mapIndexed { i, s -> if (i == 0) s else s.capitalize() }
        .joinToString("")

    extra[extraKey] = when {
        hasProperty(propertyKey) -> properties[propertyKey]
        else -> System.getenv("INKAPPLICATIONS_MINDFREAK${key.toScreamingSnakeCase()}") ?: defaultValue
    }
}
