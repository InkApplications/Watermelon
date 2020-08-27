import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

/**
 * Loads a value from project properties or from environment variables.
 * namespace for project properties is `cash.andrew.mntrail` and `MN_TRAIL_INFO`
 * for environment variables.
 */
fun Project.loadExtra(key: String, defaultValue: String = "") {
    val propertyKey = "inkapplications.watermelon.$key"
    val extraKey = key.split(".")
        .mapIndexed { i, s -> if (i == 0) s else s.capitalize() }
        .joinToString("")

    extra[extraKey] = when {
        hasProperty(propertyKey) -> properties[propertyKey]
        else -> System.getenv("INKAPPLICATIONS_WATERMELON_${key.toScreamingSnakeCase()}") ?: defaultValue
    }
}
